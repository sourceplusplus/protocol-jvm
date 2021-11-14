package spp.protocol.instrument

import kotlinx.datetime.Clock
import kotlin.jvm.Transient

class HitThrottle(private val limit: Int, step: HitThrottleStep) {

    private val step: HitThrottleStep

    @Transient
    private var lastReset: Long = -1

    @Transient
    private var hitCount = 0

    @Transient
    var totalHitCount = 0
        private set

    @Transient
    var totalLimitedCount = 0
        private set

    init {
        this.step = step
    }

    fun isRateLimited(): Boolean {
        if (hitCount++ < limit) {
            totalHitCount++
            return false
        }
        return if (Clock.System.now().toEpochMilliseconds() - lastReset > step.toMillis(1)) {
            hitCount = 1
            totalHitCount++
            lastReset = Clock.System.now().toEpochMilliseconds()
            false
        } else {
            totalLimitedCount++
            true
        }
    }
}
