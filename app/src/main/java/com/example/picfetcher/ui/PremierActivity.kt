package com.example.picfetcher.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.picfetcher.BaseApplication
import com.example.picfetcher.R
import com.example.picfetcher.databinding.ActivityPremierBinding
import com.example.picfetcher.network.APIService
import com.example.picfetcher.ui.bases.BaseActivity
import com.example.picfetcher.ui.premierFragment.PicsListContract
import com.example.picfetcher.ui.premierFragment.PicsListPresenter
import com.example.picfetcher.ui.premierFragment.PicsListFragment
import javax.inject.Inject

class PremierActivity : BaseActivity() {

    private lateinit var binding: ActivityPremierBinding

    @Inject
    lateinit var apiService: APIService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPremierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as BaseApplication).getNetworkComponent().inject(this)

        val picsListFragment = PicsListFragment(PicsListPresenter(apiService))

        displayFragment(R.id.fragment_view, picsListFragment)

    }
}