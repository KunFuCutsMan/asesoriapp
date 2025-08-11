package com.padieer.asesoriapp.domain.datetime

import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaLocalTime
import java.time.LocalTime as JavaLocalTime

class HoursJavaLocalTimeIterator(start: JavaLocalTime, val endInclusive: JavaLocalTime, val step: Long): Iterator<JavaLocalTime> {
    private var current = start
    override fun next(): JavaLocalTime {
        current = current.plusHours(step)
        return current
    }

    override fun hasNext(): Boolean {
        return current <= endInclusive
    }
}

class HourLocalTimeProgression(
        start: LocalTime,
        endInclusive: LocalTime,
        val stepHours: Long = 1,
): Iterable<JavaLocalTime>, ClosedRange<JavaLocalTime> {

    override val start = start.toJavaLocalTime()
    override val endInclusive = endInclusive.toJavaLocalTime()

    override fun iterator(): Iterator<JavaLocalTime> = HoursJavaLocalTimeIterator(start, endInclusive, stepHours)

    infix fun step(step: Long) = HoursJavaLocalTimeIterator(start, endInclusive, step)
}
