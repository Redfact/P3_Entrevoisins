package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowNeighbourActivity extends AppCompatActivity {
    @BindView(R.id.imageUser)
    ImageView imageUser;
    @BindView(R.id.ReturnButton)
    Button ReturnButton;
    @BindView(R.id.UserName0)
    TextView UserName0;
    @BindView(R.id.FavoriteButton)
    FloatingActionButton AddFavorite;
    @BindView(R.id.UserAdresse)
    TextView adress;
    @BindView(R.id.UserMail)
    TextView mail;
    @BindView(R.id.UserNumber)
    TextView number;
    @BindView(R.id.AboutMeContentText)
    TextView aboutMe;

    private NeighbourApiService mApiService= DI.getNeighbourApiService();
    private Neighbour CurNeighbour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_neighbour);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        CurNeighbour = (Neighbour)intent.getSerializableExtra("NeighbourClicked");
        Glide.with(this.getBaseContext()).load(CurNeighbour.getAvatarUrl()).into(imageUser);
        UserName0.setText(CurNeighbour.getName());
        adress.setText(CurNeighbour.getAddress());
        number.setText(CurNeighbour.getPhoneNumber());
        mail.setText("www.facebook.com/"+CurNeighbour.getName());
        aboutMe.setText(CurNeighbour.getAboutMe());

        ChangeFavoriteButtonColor(CurNeighbour.isFavorite(), AddFavorite);
    }

    @OnClick(R.id.FavoriteButton)
    void addNeighbourToFavorites() {
        List<Neighbour> Neighbours = mApiService.getNeighbours();
        Neighbours.get(Neighbours.indexOf(CurNeighbour)).setIsFavorite();
        ChangeFavoriteButtonColor(Neighbours.get(Neighbours.indexOf(CurNeighbour)).isFavorite(), AddFavorite);
    }

    @OnClick(R.id.ReturnButton)
    void navigate() {
        Intent showNeighbours = new Intent( ShowNeighbourActivity.this, ListNeighbourActivity.class);
        startActivity(showNeighbours);
    }

    void ChangeFavoriteButtonColor(boolean bool, FloatingActionButton button){
        if(bool)
            button.setImageTintList( AppCompatResources.getColorStateList(this, R.color.colorYellow));
        else
            button.setImageTintList( AppCompatResources.getColorStateList(this, R.color.colorDark));
    }

}