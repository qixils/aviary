package dev.qixils.aviary.minigames.utils

import java.time.Duration
import java.time.Instant
import kotlin.math.*

private const val conversionConstant = 1500.0
private const val conversionFactor = 173.7178
private const val defaultRating = conversionConstant
private const val defaultRating2 = (defaultRating - conversionConstant) / conversionFactor
private const val defaultDeviation = 350.0
private const val defaultDeviation2 = defaultDeviation / conversionFactor
private const val minDeviation = 30.0
private const val maxDeviation = defaultDeviation
private const val defaultVolatility = 0.06
private const val tau = 0.3
private val tau2 = tau.pow(2)
private const val epsilon = 0.000001

val ratingPeriod: Duration = Duration.ofMinutes(10)
val uncertaintyPeriod: Duration = Duration.ofDays(365 * 3)
private val c2 = calcC2(ratingPeriod, uncertaintyPeriod)

typealias Outcome = Double
const val WIN: Outcome = 1.0
const val DRAW: Outcome = 0.5
const val LOSS: Outcome = 0.0

private fun calcC2(ratingPeriod: Duration, uncertaintyPeriod: Duration): Double {
	// maxRD = sqrt(minRD^2 + c^2 * uncertaintyPeriod/ratingPeriod)
	// maxRD^2 = minRD^2 + c^2 * uncertaintyPeriod/ratingPeriod
	// c^2 * uncertaintyPeriod/ratingPeriod = maxRD^2 - minRD^2
	// c^2 = (maxRD^2 - minRD^2) * ratingPeriod/uncertaintyPeriod
	return (maxDeviation.pow(2) - minDeviation.pow(2)) * ratingPeriod.toMinutes() / uncertaintyPeriod.toMinutes()
}

abstract class Score<Self : Score<Self>>(val rating: Double, val deviation: Double) {
	val created: Instant = Instant.now()
	protected abstract fun degrade(): Self
	abstract fun estimateOutcome(opponent: Self): Outcome
	abstract fun update(opponents: List<Self>, outcomes: List<Outcome>): Self
}

class Score1(rating: Double, deviation: Double) : Score<Score1>(rating, deviation) {
	constructor() : this(defaultRating, defaultDeviation)

	companion object {
		private val q = ln(10.0) / 400.0
		private fun g(rd: Double): Double {
			return 1.0 / sqrt(1.0 + 3.0 * q.pow(2) * rd.pow(2) / PI.pow(2))
		}
	}

	override fun degrade(): Score1 {
		return Score1(rating, min(sqrt(deviation.pow(2) + c2), maxDeviation))
	}

	override fun estimateOutcome(opponent: Score1): Outcome {
		return 1.0 / (1.0 + 10.0.pow(-g(opponent.deviation) * (this.rating - opponent.rating) / 400.0))
	}

	override fun update(opponents: List<Score1>, outcomes: List<Outcome>): Score1 {
		assert(opponents.size == outcomes.size) { "opponents and outcomes must have the same size" }
		val degraded = degrade()
		if (opponents.isEmpty())
			return degraded
		return degraded._update(opponents, outcomes)
	}

	private fun _update(opponents: List<Score1>, outcomes: List<Outcome>): Score1 {
		var d2Product = 0.0
		var ratingProduct = 0.0
		for (i in opponents.indices) {
			val opponent = opponents[i]
			val outcome = outcomes[i]
			val estimatedOutcome = estimateOutcome(opponent)
			val g = g(opponent.deviation)
			ratingProduct += g * (outcome - estimatedOutcome)
			d2Product += g.pow(2) * estimatedOutcome * (1.0 - estimatedOutcome)
		}
		val d2 = 1.0 / (q.pow(2) * d2Product)
		val newR = this.rating + q / (1.0 / this.deviation.pow(2) + 1.0 / d2) * ratingProduct
		val newRD = 1.0 / sqrt(1.0 / this.deviation.pow(2) + 1.0 / d2)
		return Score1(newR, newRD)
	}
}

class Score2(val rating2: Double, val deviation2: Double, val volatility: Double) : Score<Score2>(
	rating2 * conversionFactor + conversionConstant,
	deviation2 * conversionFactor
) {
	constructor() : this(defaultRating2, defaultDeviation2, defaultVolatility)

	companion object {
		private fun g(rd: Double): Double {
			return 1.0 / sqrt(1.0 + 3.0 * rd.pow(2) / PI.pow(2))
		}
	}

	override fun degrade(): Score2 {
		return Score2(rating2, sqrt(deviation2.pow(2) + volatility.pow(2)), volatility)
	}

	override fun estimateOutcome(opponent: Score2): Outcome {
		return 1.0 / (1.0 + exp(-g(opponent.deviation2) * (this.rating2 - opponent.rating2)))
	}

	override fun update(opponents: List<Score2>, outcomes: List<Outcome>): Score2 {
		assert(opponents.size == outcomes.size) { "opponents and outcomes must have the same size" }
		if (opponents.isEmpty())
			return degrade()

		var vSum = 0.0
		var improvementSum = 0.0
		for (i in opponents.indices) {
			val opponent = opponents[i]
			val outcome = outcomes[i]
			val estimatedOutcome = estimateOutcome(opponent)
			val g = g(opponent.deviation2)

			vSum += g.pow(2) * estimatedOutcome * (1.0 - estimatedOutcome)
			improvementSum += g * (outcome - estimatedOutcome)
		}
		val v = 1.0 / vSum
		val delta = v * improvementSum
		val delta2 = delta.pow(2)
		val deviationSq = this.deviation2.pow(2)

		val a = ln(this.volatility.pow(2))
		val f = { x: Double ->
			val e = exp(x)
			e * (delta2 - deviationSq - v - e) / (2.0 * (deviationSq + v + e).pow(2)) - (x - a) / tau2
		}

		var A = a
		var B = if (delta2 > deviationSq + v) {
			ln(delta2 - deviationSq - v)
		} else {
			var k = 1
			while (f(a - k * tau) < 0) {
				k++
			}
			a - k * tau
		}
		var fA = f(A)
		var fB = f(B)
		while (abs(B - A) > epsilon) {
			val C = A + (A - B) * fA / (fB - fA)
			val fC = f(C)
			if (fC * fB <= 0) {
				A = B
				fA = fB
			} else {
				fA /= 2.0
			}
			B = C
			fB = fC
		}
		val newVolatility = exp(A / 2.0)
		val degradedNewDeviation2 = sqrt(deviationSq + newVolatility.pow(2))
		val newDeviation2 = 1.0 / sqrt(1.0 / degradedNewDeviation2.pow(2) + 1.0 / v)
		val newRating2 = this.rating2 + newDeviation2.pow(2) * improvementSum
		return Score2(newRating2, newDeviation2, newVolatility)
	}
}
