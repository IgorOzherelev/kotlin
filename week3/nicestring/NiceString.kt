package nicestring


fun Char.isVowel() : Boolean = this.equals('a') ||
        this.equals('e') ||
        this.equals('i') ||
        this.equals('u') ||
        this.equals('o');



fun String.isNice(): Boolean {

    if (this.isEmpty()) return false;

    fun ruleFirst(input: String) : Boolean = (!input.contains("bu")
            && !input.contains("ba")
            && !input.contains("be"))

    fun ruleSecond(input: String) : Boolean = input.toCharArray()
            .filter {elem -> elem.isVowel() }.size >= 3

    fun ruleThird(input: String): Boolean{
        val arrayInput: CharArray = input.toCharArray();

        if (arrayInput.size == 2){
            return arrayInput[0] == arrayInput[1]
        }

        for (i in 1 until arrayInput.size - 1){
            if (arrayInput[i - 1] == arrayInput[i] ||
                    arrayInput[i + 1] == arrayInput[i]) return true;
        }

        return false;
    }

    return listOf<Boolean>(ruleFirst(this),
            ruleSecond(this),
            ruleThird(this)).count { it } >= 2
}