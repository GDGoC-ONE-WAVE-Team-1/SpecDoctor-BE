package com.specdoctor.domain.invalidactivity.dto.response;

import com.specdoctor.domain.activity.dto.response.SearchResultResponseDto;
import com.specdoctor.domain.invalidactivity.entity.InvalidActivity;

public record InvalidActivityResponseDto(
	String name,
	String operationEntity,
	String finance,
	String financeOpen,
	String leaderSelection
) implements SearchResultResponseDto {

	public static InvalidActivityResponseDto of(
		InvalidActivity invalidActivity
	) {
		return new InvalidActivityResponseDto(
			invalidActivity.getName(),
			invalidActivity.getOperationEntity(),
			invalidActivity.getFinance(),
			invalidActivity.getFinanceOpen(),
			invalidActivity.getLeaderSelection()
		);
	}
}
