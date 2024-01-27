class Tiempo(hora:Int){
    private var hora = 0
        set(value) {
            require(value < 24) { "Un dia no tiene mas de 24 horas" }
            field = value
        }

    constructor(hora: Int, min:Int):this(hora){
        this.min = min
    }

    constructor(hora: Int, min:Int, seg: Int):this(hora,min ){
        this.seg = seg
    }

    private var min = 0
        set(value) {
            this.hora += value / 60
            field = value % 60
        }

    private var seg = 0
        set(value) {
            this.min += value / 60
            field = value % 60
        }

    init {
        this.hora = hora
    }
    override fun toString(): String {
        return "${this.hora}h, ${min}m, ${seg}s"
    }

}

fun main() {
    val hora = Tiempo(5,73,98)
    val hora2 = Tiempo(2)
    println(hora.toString())
    println(hora2.toString())
}
