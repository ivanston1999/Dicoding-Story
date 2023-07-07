package com.submission.dicodingstory.response

import com.submission.dicodingstory.model.Story

data class StoryResponse(
    val error: Boolean,
    val message: String,
    val listStory: List<Story>,
)