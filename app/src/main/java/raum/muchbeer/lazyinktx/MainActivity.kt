package raum.muchbeer.lazyinktx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import raum.muchbeer.lazyinktx.adapter.RestaurantAdapter
import raum.muchbeer.lazyinktx.model.YelpRestaurant
import raum.muchbeer.lazyinktx.utility.Status
import raum.muchbeer.lazyinktx.viewmodel.YelpViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var yelpViewModel: YelpViewModel
    private lateinit var adapter: RestaurantAdapter
    private val taxCollector = TaxCollector()

    //This help initilize the Accountant when it needed meaning when the salary exceed the threshold

    private val accountantDelegete = lazy {   Accountant()    }

    private val accountant by accountantDelegete
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        yelpViewModel = ViewModelProvider(this).get(YelpViewModel::class.java)
        setUpLiveData()

        btnRetry.setOnClickListener {
            setUpLiveData()
        }
      //  yelpViewModel.retrieveResponse()
        val salaries = listOf(25, 89, 230)
        accountantDelegete.isInitialized()
        for (salary in salaries) {
            (taxCollector.payTaxes(salary))
            if (salary > Threshold_Salary) {
                accountantDelegete.isInitialized()
                accountant.findTaxSaving()
            }
        }
    }


    private fun setUpLiveData() {
        yelpViewModel.retrieveResponse().observe(this, {

            it?.let {
                when(it.status) {
                    Status.SUCCESS -> {
                        Log.i("MainActivity", "Check if it entered")
                        progressBar.visibility = View.GONE
                        recyclerView.visibility= View.VISIBLE
                        btnRetry.visibility = View.GONE
                        it.data?.let { data ->
                            setUI(data)
                        }
                    }

                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                        btnRetry.visibility=View.GONE
                    }

                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.GONE
                        btnRetry.visibility = View.VISIBLE

                       snackBarShow( it.message!! )
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val mSearchMenuItem = menu?.findItem(R.id.app_bar_search)
        val searchView = mSearchMenuItem?.actionView as SearchView
        search(searchView)
        return super.onCreateOptionsMenu(menu)
    }


    private fun search(searchView: SearchView) {

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                (recyclerView.adapter as RestaurantAdapter).filter(newText)
                return true
            }
        })
    }
    private fun snackBarShow(message: String) {
        val snackbar = Snackbar.make(constraintLayout, message, Snackbar.LENGTH_LONG)
        snackbar.show()   }


    private fun setUI(data: List<YelpRestaurant>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        adapter = RestaurantAdapter(RestaurantAdapter.FoodListener {restaurant->
            Toast.makeText(this, "You clicked ${restaurant.name}", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FoodDetailActivity::class.java)
            intent.putExtra(RESTAURANT_KEY, restaurant)
            startActivity(intent)


           /* USING THE NAVIGATION COMPONENT
                 yelpViewModel.onRestaurantClick(restaurant)
                     yelpViewModel.navigateRestaurant.observe(this, Observer { restaurant->
                            restaurant?.let {
                                this.findNavController().navigate(RestaurantFragmentDirections.
                                actionRestaurant(restaurant))
                    yelpViewModel.onRestaurantClickedAlready(it)
                }

            })
           * */
        })
        recyclerView.adapter = adapter

        adapter.modifyList(data)
       // adapter.submitList(data)
    }

    class  TaxCollector {

        init {
            Log.i("PayTax","init tax collector")
        }
        fun payTaxes(salary:Int) = Log.i("PayTax","Paying taxes  on salary of ${salary} ....")
    }

  class  Accountant {

        init {

            Log.i("PayTax","init accountant - this is hard work!")
        }
        fun findTaxSaving() = Log.i("PayTax","Found tax savings!")
    }

    companion object {
        private const val Threshold_Salary = 100
        const val RESTAURANT_KEY = "rest_key"
    }

}