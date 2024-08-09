package com.movieapp.cinemate.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RatingModel {
	
	private Integer movieId;
	private String title;
	private Integer rating;
}
