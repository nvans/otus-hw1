package com.github.nvans.hw1

import kotlin.math.abs
import kotlin.math.sqrt

class QuadraticEquationSolver {

    /**
     * The function solves a quadratic equation in form
     * 'ax^2 + bx + c = 0'
     * using [discriminant](https://en.wikipedia.org/wiki/Discriminant).
     *
     * @return array consisting of two, one or zero roots
     */
    fun solve(a: Double, b: Double, c: Double): Array<Double> {
        if (abs(a) < DEFAULT_EPSILON)
            throw IllegalArgumentException("An argument 'a' must not be equal to zero.")
        if (!(a.isFinite() && b.isFinite() && c.isFinite()))
            throw IllegalArgumentException("Arguments must be finite numbers.")

        val ds = b * b - 4 * a * c

        return when {
            ds < -DEFAULT_EPSILON -> {
                emptyArray()
            }
            abs(ds) < DEFAULT_EPSILON -> {
                val root = -b / (2 * a)
                arrayOf(root, root)
            }
            else -> {
                val rootPart = sqrt(ds) / (2 * a)
                arrayOf(
                    -b + rootPart,
                    -b - rootPart
                )
            }
        }
    }

    internal companion object {
        internal const val DEFAULT_EPSILON = 1e-7
    }
}
