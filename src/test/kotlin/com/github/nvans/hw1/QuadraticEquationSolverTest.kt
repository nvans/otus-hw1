package com.github.nvans.hw1

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource


// С учетом того, что дискриминант тоже нельзя сравнивать с 0 через знак равенства,
// подобрать такие коэффициенты квадратного уравнения для случая одного корня кратности два,
// чтобы дискриминант был отличный от нуля, но меньше заданного эпсилон.
// Эти коэффициенты должны заменить коэффициенты в тесте из п. 7.
// При необходимости поправить реализацию квадратного уравнения.
// Посмотреть какие еще значения могут принимать числа типа double, кроме числовых и написать тест с их использованием на все коэффициенты. solve должен выбрасывать исключение.
// Написать минимальную реализацию функции solve, которая удовлетворяет тесту из п.13.
// Сделать merge request/pull request и ссылку на него указать при сдач
class QuadraticEquationSolverTest {

    private val solver = QuadraticEquationSolver()

    /**
     * Написать тест, который проверяет, что для уравнения x^2+1 = 0 корней нет
     * (возвращается пустой массив)
     */
    @Test
    fun `'x^2 + 1 = 0' should has no roots`() {
        Assertions
            .assertThat(solver.solve(a = 1.0, b = 0.0, c = 1.0))
            .describedAs("x^2 + 1 = 0 should has no roots")
            .isEmpty()
    }

    /**
     * Написать тест, который проверяет, что для уравнения x^2-1 = 0
     * есть два корня кратности 1 (x1=1, x2=-1)
     */
    @Test
    fun `'x^2 - 1 = 0' should has 2 different roots`() {
        Assertions
            .assertThat(solver.solve(a = 1.0, b = 0.0, c = -1.0))
            .isEqualTo(arrayOf(1.0, -1.0))
    }

    /**
     * Написать тест, который проверяет, что для уравнения x^2+2x+1 = 0
     * есть один корень кратности 2 (x1= x2 = -1).
     *
     * С учетом того, что дискриминант тоже нельзя сравнивать с 0 через знак равенства,
     * подобрать такие коэффициенты квадратного уравнения для случая одного корня кратности два,
     * чтобы дискриминант был отличный от нуля, но меньше заданного эпсилон.
     * Эти коэффициенты должны заменить коэффициенты в тесте из п. 7.
     */
    @Test
    fun `'x^2+2x+1 = 0' should have two same roots`() {
        Assertions
            .assertThat(solver.solve(a = 2.0, b = 4.0, c = 1.99999999))
            .isEqualTo(arrayOf(-1.0, -1.0))
    }

    /**
     * Написать тест, который проверяет, что коэффициент a не может быть равен 0.
     * В этом случае solve выбрасывает исключение.
     * Примечание. Учесть, что a имеет тип double и сравнивать с 0 через == нельзя.
     */
    @Test
    fun `When 'a' is zero should throw exception`() {
        Assertions
            .assertThatThrownBy {
                solver.solve(a = 0.0, b = 1.0, c = 2.0)
            }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    @ParameterizedTest
    @ValueSource(doubles = [
        Double.NaN,
        Double.POSITIVE_INFINITY,
        Double.NEGATIVE_INFINITY]
    )
    fun `When parameter is not a finite number then should fail validation`(
        notFiniteParam: Double
    ) {
        Assertions
            .assertThatThrownBy {
                solver.solve(a = notFiniteParam, b = DEFAULT_VALUE, c = DEFAULT_VALUE)
            }
            .withFailMessage { "Parameter 'a' should fail validation. a = $notFiniteParam" }
            .isInstanceOf(IllegalArgumentException::class.java)

        Assertions
            .assertThatThrownBy {
                solver.solve(a = DEFAULT_VALUE, b = notFiniteParam, c = DEFAULT_VALUE)
            }
            .withFailMessage { "Parameter 'b' should fail validation. b = $notFiniteParam" }
            .isInstanceOf(IllegalArgumentException::class.java)

        Assertions
            .assertThatThrownBy {
                solver.solve(a = DEFAULT_VALUE, b = DEFAULT_VALUE, c = notFiniteParam)
            }
            .withFailMessage { "Parameter 'c' should fail validation. c = $notFiniteParam" }
            .isInstanceOf(IllegalArgumentException::class.java)
    }


    private companion object {
        const val DEFAULT_VALUE = 1.0
    }
}