package com.qautomatron.kaper.core.visibility

/**
 * Element condition list
 */
enum class Condition {
    VISIBLE, INVISIBLE, PRESENCE, ABSENCE
}

val visible = Condition.VISIBLE
val invisible = Condition.INVISIBLE
val presence = Condition.PRESENCE
val absence = Condition.ABSENCE