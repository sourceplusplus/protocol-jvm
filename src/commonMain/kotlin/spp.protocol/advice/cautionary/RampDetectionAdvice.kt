package spp.protocol.advice.cautionary

import spp.protocol.advice.AdviceCategory
import spp.protocol.advice.AdviceType
import spp.protocol.advice.ArtifactAdvice
import spp.protocol.artifact.ArtifactQualifiedName

/**
 * todo: description.
 *
 * @since 0.1.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
class RampDetectionAdvice(
    artifact: ArtifactQualifiedName,
    regressionSource: ArtifactQualifiedName,
    regression: SimpleRegression
) : ArtifactAdvice(artifact, AdviceCategory.CAUTIONARY, AdviceType.RampDetectionAdvice) {

    var regression: SimpleRegression = regression
        private set

    fun updateRegression(regression: SimpleRegression) {
        this.regression = regression
        triggerUpdated()
    }

    override fun isSameArtifactAdvice(artifactAdvice: ArtifactAdvice): Boolean {
        return artifactAdvice is RampDetectionAdvice && artifactAdvice.artifact == artifact
    }

    override fun updateArtifactAdvice(artifactAdvice: ArtifactAdvice) {
        updateRegression((artifactAdvice as RampDetectionAdvice).regression)
    }

    interface SimpleRegression {
        val n: Long
        val intercept: Double
        val slope: Double
        val sumSquaredErrors: Double
        val totalSumSquares: Double
        val xSumSquares: Double
        val sumOfCrossProducts: Double
        val regressionSumSquares: Double
        val meanSquareError: Double
        val r: Double
        val rSquare: Double
        val interceptStdErr: Double
        val slopeStdErr: Double
        val slopeConfidenceInterval: Double
        val significance: Double

        fun predict(x: Double): Double
    }

    override fun toString(): String {
        return "$type{$artifact - Confidence: ${regression.rSquare}"
    }
}