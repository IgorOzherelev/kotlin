package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var rightPosition = 0;
    var wrongPosition = 0;

    val secretArray  = secret.toCharArray();

    for ((ind, ch) in guess.withIndex()){
        if (secret[ind] == ch){
            rightPosition++;
            secretArray[ind] = '1';
        }
    }

   for ((ind, ch) in guess.withIndex()) {
        if (secret[ind] != ch){
            val chIndex = secretArray.indexOf(ch);
            if (chIndex != -1 && guess[chIndex] != ch) {
                wrongPosition++;
                secretArray[chIndex] = '1';
            }
        }
    }


    return Evaluation(rightPosition, wrongPosition);
}
