interface Base {
    val getX: Int
    fun print()
}

class BaseImpl(val x: Int) : Base {
    override val getX: Int get() = x

    override fun print() {
        println(x)
    }
}

class Derived(b: Base) : Base by b {
    fun times(value: Int): Int = getX * value
}


val b = BaseImpl(10)
val derived = Derived(b)
derived.times(10)
derived.print()
