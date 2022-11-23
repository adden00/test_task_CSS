package com.example.domain.models

data class RateItem(
    val name: String,
    val rate: Double,
) {
    override fun equals(other: Any?): Boolean {
        return this.name == (other as RateItem).name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}