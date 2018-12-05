package fr.epita.aaron.hellogame

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    val gameList = arrayListOf<Games>()
    val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
    val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
    val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(jsonConverter).build()
    val service: WebService = retrofit.create(WebService::class.java)
    val callback = object : Callback<List<Games>> {
        override fun onFailure(call: Call<List<Games>>?, t: Throwable?) {
            Log.d("TAG", "WebService call failed")
        }
        override fun onResponse(call: Call<List<Games>>?, response: Response<List<Games>>?) {
            if (response != null) {
                if (response.code() == 200) {
                    // We got our data !

                    val value = response.body()

                    if (value != null) {

                        gameList.addAll(value)

                        Log.d("TAG", "WebService success : " + gameList.size)

                        val randomTab = ArrayList<Int>()

                        for (i in 0..4) {

                            val randomInt = (0..gameList.size - 1).shuffled().first()

                            if (!randomTab.contains(randomInt)) {
                                randomTab.add(randomInt)
                            }

                        }

                        imageTopLeft.setImageResource(getImage(gameList[randomTab.get(0)].id!!))

                        imageTopLeft.setOnClickListener{

                            val explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                            explicitIntent.putExtra("game_selector", gameList[randomTab.get(0)].id)
                            startActivity(explicitIntent)
                        }

                        imageTopRight.setImageResource(getImage(gameList[randomTab.get(1)].id!!))

                        imageTopRight.setOnClickListener{

                            val explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                            explicitIntent.putExtra("game_selector", gameList[randomTab.get(1)].id)
                            startActivity(explicitIntent)
                        }

                        imageBottomLeft.setImageResource(getImage(gameList[randomTab.get(2)].id!!))

                        imageBottomLeft.setOnClickListener{

                            val explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                            explicitIntent.putExtra("game_selector", gameList[randomTab.get(2)].id)
                            startActivity(explicitIntent)
                        }

                        imageBottomRight.setImageResource(getImage(gameList[randomTab.get(3)].id!!))

                        imageBottomRight.setOnClickListener{

                            val explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                            explicitIntent.putExtra("game_selector", gameList[randomTab.get(3)].id)
                            startActivity(explicitIntent)
                        }

                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        service.listGames().enqueue(callback)
    }


    fun getImage(id: Int): Int {

        var game_selector = 0

        when(id) {

            1 -> game_selector = R.drawable.tictactoe
            2 -> game_selector = R.drawable.hangman
            3 -> game_selector = R.drawable.sudoku
            4 -> game_selector = R.drawable.battleship
            5 -> game_selector = R.drawable.mineswepper
            6 -> game_selector = R.drawable.gameoflife
            7 -> game_selector = R.drawable.memory
            8 -> game_selector = R.drawable.simon
            9 -> game_selector = R.drawable.slidingpuzzle
            10 -> game_selector = R.drawable.mastermind

        }

        return game_selector
    }
}