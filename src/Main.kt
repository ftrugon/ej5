import kotlin.concurrent.timer

/**
 * Clase que representa un instante de tiempo en horas, minutos y segundos.
 *
 * @property hora La hora del tiempo.
 * @property min Los minutos del tiempo (0-59).
 * @property seg Los segundos del tiempo (0-59).
 *
 * @constructor Crea un objeto [Tiempo] con la hora especificada.
 * @param hora La hora del tiempo.
 *
 * @constructor Crea un objeto [Tiempo] con la hora y los minutos especificados.
 * @param hora La hora del tiempo.
 * @param min Los minutos del tiempo.
 *
 * @constructor Crea un objeto [Tiempo] con la hora, los minutos y los segundos especificados.
 * @param hora La hora del tiempo.
 * @param min Los minutos del tiempo.
 * @param seg Los segundos del tiempo.
 */

class Tiempo(private var hora:Int){

    constructor(hora: Int, min:Int):this(hora){
        this.min = min
    }

    constructor(hora: Int, min:Int, seg: Int):this(hora,min ){
        this.seg = seg
    }

    /**
     * Los minutos del tiempo (0-59).
     * Se actualiza automáticamente cuando se asigna un nuevo valor.
     */
    private var min = 0
        set(value) {
            this.hora += value / 60
            field = value % 60
        }

    /**
     * Los segundos del tiempo (0-59).
     * Se actualiza automáticamente cuando se asigna un nuevo valor.
     */
    private var seg = 0
        set(value) {
            this.min += value / 60
            field = value % 60
        }

    /**
     * Inicializador que verifica que la hora no sea mayor a 24.
     * Lanza una excepción si la condición no se cumple.
     */
    init {
        require(this.hora < 24) { "La hora no puede ser mayor a 24" }
    }

    /**
     * Representación en cadena del objeto [Tiempo].
     * @return Una cadena que representa la hora, los minutos y los segundos.
     */
    override fun toString(): String {
        return "${this.hora}h, ${this.min}m, ${this.seg}s"
    }

    /**
     * Le incrementa un tiempo a nuestro tiempo
     * @param t el tiempo a incrementar
     * @return un booleano para ver si se ha incrementado o no
     */
    fun incrementar(t:Tiempo):Boolean {
        val secunhora = Tiempo(this.hora,this.min,this.seg)
        secunhora.seg += t.seg
        secunhora.min += t.min
        secunhora.hora += t.hora

        if (secunhora.hora >= 24) return false else{
            this.seg += t.seg
            this.min += t.min
            this.hora += t.hora
            return true
        }
    }

    /**
     * Le retsta un tiempo a nuestro tiempo
     * @param t el tiempo a decrementar
     * @return un booleano para ver si se ha decrementado o no
     */
    fun decrementar(t: Tiempo): Boolean {
        val copiaOriginal = Tiempo(this.hora, this.min, this.seg)

        this.seg -= t.seg
        this.min -= t.min
        this.hora -= t.hora

        if (this.seg < 0) {
            this.min -= 1
            this.seg += 60
        }

        if (this.min < 0) {
            this.hora -= 1
            this.min += 60
        }

        if (this.hora < 0 && this.min < 0 && this.seg < 0) {
            this.hora = copiaOriginal.hora
            this.min = copiaOriginal.min
            this.seg = copiaOriginal.seg
            return false
        }

        return true
    }

    /**
     * compara si un tiempo es mayor menor o igual a otro
     * @param t el tiempo a comparar
     * @return 1 0 o -1 dependiendo de si es mayor menor o igual a otro
     */
    fun comparar(t:Tiempo): Int {
        if (this.hora < t.hora || (this.hora == t.hora && this.min < t.min) || (this.hora == t.hora&& this.min == t.min && this.seg < t.seg)){
            return -1
        }else if (this.hora == t.hora && this.min == t.min && this.seg == t.seg){
            return 0
        }else{
            return 1
        }
    }

    /**
     * Copia el tiempo en una nueva variable
     * @return el nuevo tiempo creado
     */
    fun copiar():Tiempo{
        val nuevotiempo = Tiempo(this.hora,this.min,this.seg)
        return nuevotiempo
    }


    fun copiar(t:Tiempo):Tiempo {
        //Las siguientes 4 lineas sirven para pedir el tiempo, creo que es redundante porque lo puedes pedir al crear tiempo pero no entendia el enunciado y lo puse igual aqui
        val tiempot = pedireltiempo()
        t.hora = tiempot[0]
        t.min = tiempot[1]
        t.seg = tiempot[2]
        val nuevotiempo = Tiempo(t.hora,t.min,t.seg)
        return nuevotiempo
    }

    /**
     * Suma el tiempo de otro tiempo
     * @param t el tiempo a sumar
     * @return el tiempo sumado o null si la suma daba el tiempo mayor a 24:59:59
     */
    fun sumar(t:Tiempo):Tiempo? {
        val copiaOriginal = Tiempo(this.hora, this.min, this.seg)

        this.hora += t.hora
        this.min += t.min
        this.seg += t.seg

        if (this.hora > 24) {
            this.hora = copiaOriginal.hora
            this.min = copiaOriginal.min
            this.seg = copiaOriginal.seg
            return null
        }

        return this
    }

    /**
     * Resta el tiempo de itro tiempo
     * @param t el tiempo a restar
     * @return el tiempo restado o null si la resta daba el tiempo menor a 00:00:00
     */
    fun restar(t:Tiempo):Tiempo?{
        val copiaOriginal = Tiempo(this.hora, this.min, this.seg)

        this.seg -= t.seg
        this.min -= t.min
        this.hora -= t.hora

        if (this.seg < 0) {
            this.min -= 1
            this.seg += 60
        }

        if (this.min < 0) {
            this.hora -= 1
            this.min += 60
        }

        if (this.hora < 0 && this.min < 0 && this.seg < 0) {
            this.hora = copiaOriginal.hora
            this.min = copiaOriginal.min
            this.seg = copiaOriginal.seg
            return null
        }

        return this
    }

    /**
     * Compara si el tiempo es mayor a otro tiempo
     * @param t El tiempo con el que se compara
     * @return true o false si es mayor o menor
     */
    fun esMayorQue(t:Tiempo):Boolean {
        //Las siguientes 4 lineas sirven para pedir el tiempo, creo que es redundante porque lo puedes pedir al crear tiempo pero no entendia el enunciado y lo puse igual aqui
        val tiempot = pedireltiempo()
        t.hora = tiempot[0]
        t.min = tiempot[1]
        t.seg = tiempot[2]
        if (this.hora < t.hora || (this.hora == t.hora && this.min < t.min) || (this.hora == t.hora&& this.min == t.min && this.seg < t.seg)) {
            return false
        }else return true
    }

    /**
     * Compara si el tiempo es menor a otro tiempo
     * @param t El tiempo con el que se compara
     * @return true o false si es mayor o menor
     */
    fun esMenorQue(t:Tiempo):Boolean {
        val tiempot = pedireltiempo()
        t.hora = tiempot[0]
        t.min = tiempot[1]
        t.seg = tiempot[2]
        if (this.hora < t.hora || (this.hora == t.hora && this.min < t.min) || (this.hora == t.hora&& this.min == t.min && this.seg < t.seg)) {
            return true
        }else return false
    }
}

/**
 * Es una funcion que pide la hora, minutos y segundos
 * @return una lista con las horas
 */
fun pedireltiempo():List<Int>{
    val lista = mutableListOf<Int>()

    var estado = false
    do {
        print("Dime la hora: ")
        // Le pongo -1 para que en el siguiente if me lo heche para atras y me lo vuelva a pedir
        val hora = readln().toIntOrNull() ?: -1
        if (hora in 0..24) {
            lista.add(hora)
            estado = true
        }else println("Hora es un campo obligatorio y tiene que estar entre 1 y 24")

    }while (!estado)

    print("Dime los minutos (este campo puede estar vacio): ")
    val min = readln().toIntOrNull() ?: 0
    lista.add(min)
    print("Dime los segundos (este campo puede estar vacio): ")
    val seg = readln().toIntOrNull() ?: 0
    lista.add(seg)

    return lista
}

/**
 * Main es donde se crean las horas y las cosas
 */
fun main() {


    val tiempohora1= pedireltiempo()
    val hora1 = Tiempo(tiempohora1[0],tiempohora1[1],tiempohora1[2])
    println(hora1)

}
