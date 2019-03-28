/*
 * Copyright (c)  2019  SKERNA
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.skerna.commons.slbase


/**
 * Crea un singleton con parametros
 * @param creator
 * @param A --> El metodo factory para crear la instancia
 * @param T --> El tipo a retornar
 *
 *
 * Ejemplo es util para retorn una instancia unica como por ejemplo jdbc Client
 *
 *      companion object JDBC: SingletonHolder<JDBCClient, ApplicationContext>({context->
 *           /*val vertx = context.vertx
 *           val config = context.configuration
 *
 *           var postgreSQLClientConfig =  JsonObject()
 *           .put("driver_class", config.postgres.db_driver)
 *           .put("user",config.postgres.db_user)
 *           .put("password",config.postgres.db_password)
 *           .put("max_pool_size", config.postgres.db_max_pool)
 *           .put("initial_pool_size", config.postgres.db_min_pool)
 *           .put("url",config.postgres.db_host)
 *           .put("max_statements",5)
 *           .put("max_statements_per_connection",5)
 *           val client = JDBCClient.createShared(vertx, postgreSQLClientConfig)
 *           client
 *           })*/
 */
abstract class AbstractFactory<T>{

    internal var instance: T? = null

    /**
     * getInstance, retorna la instancia actualmente referenciada por el
     * campo instance
     * @param T
     * @throws IllegalStateException
     */
    fun getInstanceOrThrow():T{
        if(instance == null){
            throw IllegalStateException("Component not initialized")
        }
        return instance!!
    }

    /**
     * getInstance, retorna un valor opcional, de esta forma el cliente
     * puede ejecutar operaciones optionales
     * ejemplo: getInstace()?.let  { } // si esta presente ejecuta esto
     * campo instance
     * @param T
     * @throws IllegalStateException
     */
    fun getInstance():T?{
        return instance
    }
}


/**
 * Factory, soporta solamente 1 parametro, sin embargo se puede usar
 * un dataclass apra pasar varios prametros
 */
open  class FactorySimple<T>(creator: () -> T): AbstractFactory<T>() {
    private var creator: (() -> T)? = creator


    /**
     * createShadred, crea un objeto compartido por varios cliente,
     * haciendo uso del factory, la referencia a la instancia generada
     * es mantenida localmente y se retornara en futuras llamdas
     * @return T
     */
    fun createShadred(): T {
        val i = instance
        if (i != null) {
            return i
        }
        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = create()
                instance = created
                creator = null
                created
            }
        }
    }

    /**
     * createNew, crea una nueva instancia usando el factory
     * la referencia del objeto no es mantenido por el holder
     */
    fun create():T{
        return creator!!()
    }

}


/**
 * Factory, soporta solamente 1 parametro, sin embargo se puede usar
 * un dataclass apra pasar varios prametros
 */
open  class Factory<T, in A>(creator: (A) -> T): AbstractFactory<T>() {
    private var creator: ((A) -> T)? = creator


    /**
     * createShadred, crea un objeto compartido por varios cliente,
     * haciendo uso del factory, la referencia a la instancia generada
     * es mantenida localmente y se retornara en futuras llamdas
     * @return T
     */
    fun createShadred(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }
        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = create(arg)
                instance = created
                creator = null
                created
            }
        }
    }

    /**
     * createNew, crea una nueva instancia usando el factory
     * la referencia del objeto no es mantenido por el holder
     */
   fun create(arg: A):T{
        if(arg == null){
            throw IllegalStateException("Args not allowed null or empty")
        }
        return creator!!(arg)
    }

}

/**
 * FactoryA2, soporta hasta 2 diferentes parametros
 */
open  class FactoryA2<T, in A,B>(creator: (A,B) -> T): AbstractFactory<T>() {
    private var creator: ((A,B) -> T)? = creator

    /**
     * createShadred, crea un objeto compartido por varios cliente,
     * haciendo uso del factory, la referencia a la instancia generada
     * es mantenida localmente y se retornara en futuras llamdas
     * @return T
     */
    fun createShadred(argA: A,argB: B): T {
        val i = instance
        if (i != null) {
            return i
        }
        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = create(argA, argB)
                instance = created
                creator = null
                created
            }
        }
    }

    /**
     * createNew, crea una nueva instancia usando el factory
     * la referencia del objeto no es mantenido por el holder
     */
    fun create(argA: A,argB: B):T{
        if(argA == null || argB==null){
            throw IllegalStateException("Args not allowed null or empty")
        }
        return creator!!(argA,argB)
    }

}

/**
 * Factory A3 soporta hasta tres diferentes tipos de parametros
 */
open  class FactoryA3<T, in A,B,C>(creator: (A,B,C) -> T): AbstractFactory<T>() {
    private var creator: ((A,B,C) -> T)? = creator

    /**
     * createShadred, crea un objeto compartido por varios cliente,
     * haciendo uso del factory, la referencia a la instancia generada
     * es mantenida localmente y se retornara en futuras llamdas
     * @return T
     */
    fun createShadred(argA: A,argB: B,argC:C): T {
        val i = instance
        if (i != null) {
            return i
        }
        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = create(argA, argB, argC)
                instance = created
                creator = null
                created
            }
        }
    }

    /**
     * createNew, crea una nueva instancia usando el factory
     * la referencia del objeto no es mantenido por el holder
     */
    fun create(argA: A,argB: B,argC: C):T{
        if(argA == null || argB==null){
            throw IllegalStateException("Args not allowed null or empty")
        }
        return creator!!(argA,argB,argC)
    }

}
