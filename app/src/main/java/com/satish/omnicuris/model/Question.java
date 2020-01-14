package com.satish.omnicuris.model;

import java.util.List;

public class Question{
	private String question;
	private List<String> answers;
	private int correctIndex;

	public void setQuestion(String question){
		this.question = question;
	}

	public String getQuestion(){
		return question;
	}

	public void setAnswers(List<String> answers){
		this.answers = answers;
	}

	public List<String> getAnswers(){
		return answers;
	}

	public void setCorrectIndex(int correctIndex){
		this.correctIndex = correctIndex;
	}

	public int getCorrectIndex(){
		return correctIndex;
	}

	@Override
 	public String toString(){
		return 
			"Question{" + 
			"question = '" + question + '\'' + 
			",answers = '" + answers + '\'' + 
			",correctIndex = '" + correctIndex + '\'' + 
			"}";
		}
}