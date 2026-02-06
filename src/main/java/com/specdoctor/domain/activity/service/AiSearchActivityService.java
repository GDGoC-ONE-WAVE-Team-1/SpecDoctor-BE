package com.specdoctor.domain.activity.service;

import java.util.Map;

import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

import com.specdoctor.domain.activity.dto.response.ActivityInfoResponseDto;
import com.specdoctor.domain.activity.dto.response.SearchActivityResponseDto;
import com.specdoctor.domain.activity.dto.response.SearchResultResponseDto;
import com.specdoctor.domain.activity.entity.enums.Category;
import com.specdoctor.domain.invalidactivity.dto.response.InvalidActivityResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiSearchActivityService {

	private final ChatModel chatModel;

	public SearchActivityResponseDto execute(String activityName) {
		BeanOutputConverter<AiEvaluationResult> converter = new BeanOutputConverter<>(AiEvaluationResult.class);

		PromptTemplate promptTemplate = getPromptTemplate();
		Prompt prompt = promptTemplate.create(Map.of(
			"activityName", activityName,
			"format", converter.getFormat()
		));

		AiEvaluationResult result = converter.convert(chatModel.call(prompt).getResult().getOutput().getText());

		String finalName = (result.name() != null && !result.name().isBlank()) ? result.name() : activityName;

		if (result.isGoodActivity()) {
			SearchResultResponseDto infoDto = new ActivityInfoResponseDto(
				finalName,
				result.description(),
				result.link(),
				parseCategory(result.category())
			);
			return new SearchActivityResponseDto(true, infoDto);
		} else {
			SearchResultResponseDto invalidDto = new InvalidActivityResponseDto(
				finalName,
				result.operationEntityReason(),
				result.financeReason(),
				result.financeOpenReason(),
				result.leaderSelectionReason()
			);
			return new SearchActivityResponseDto(false, invalidDto);
		}
	}

	private static @NonNull PromptTemplate getPromptTemplate() {
		String promptText = """
			Evaluate if the activity "{activityName}" is a "good activity" based on the following criteria:
			1. The operating entity (corporate or student body) is clearly disclosed.
			2. Total activity fees and detailed usage (settlement) are regularly disclosed.
			3. Accounting book verification or cash receipt issuance is not refused.
			4. The representative selection process is democratic and transparent.

			If it violates ALL 4 criteria, set isGoodActivity to false. Provide detailed reasons for failure in:
			- 'operationEntityReason' (for criterion 1)
			- 'financeReason' (for criterion 2)
			- 'financeOpenReason' (for criterion 3)
			- 'leaderSelectionReason' (for criterion 4)

			Otherwise (if it meets at least one criterion), set isGoodActivity to true. Fill 'name', 'description', 'link', and 'category'.
			For 'category', choose the best fit from: IT, DEVELOPMENT, DESIGN, PLANNING, MARKETING.
			
			If a reason is not applicable (e.g. because the activity is good), leave it null or empty.

			IMPORTANT: Please provide all text fields (description, reasons, etc.) in KOREAN.

			{format}
			""";

		return new PromptTemplate(promptText);
	}

	private Category parseCategory(String categoryStr) {
		try {
			if (categoryStr == null) return Category.IT; // Default or handle error
			return Category.valueOf(categoryStr.toUpperCase());
		} catch (IllegalArgumentException e) {
			return Category.IT; // Fallback
		}
	}

	// Internal record for AI response
	public record AiEvaluationResult(
		boolean isGoodActivity,
		String name,
		String description,
		String link,
		String category,
		String operationEntityReason,
		String financeReason,
		String financeOpenReason,
		String leaderSelectionReason
	) {}
}
