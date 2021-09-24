package br.com.franmoraes.githubrepositories.data.mapper

abstract class BaseMapper<IN, OUT> {
    abstract fun transform(inputObject: IN): OUT

    fun transform(inputObjectList: List<IN>): List<OUT>{
        return inputObjectList.map(::transform)
    }
}