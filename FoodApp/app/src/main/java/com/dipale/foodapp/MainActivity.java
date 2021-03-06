package com.dipale.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //public static boolean lsUp = false, lsDown = false;
    ListView lsFood;
    ScrollView scroll;
    ImageView im1, im2, im3;
    TextView tx1, tx2, tx3;
    ArrayList<Food> foods;
    EditText search;
   // static int[] location = {0,1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inItView();

        foods = foods();

        im1.setImageResource(foods.get(0).img);
        tx1.setText(foods.get(0).name);
        im2.setImageResource(foods.get(1).img);
        tx2.setText(foods.get(1).name);
        im3.setImageResource(foods.get(2).img);
        tx3.setText(foods.get(2).name);

        ListAdapter listAdapter = new ListAdapter(
                MainActivity.this,
                R.layout.item_grid,
                foods.size(),
                foods);
        lsFood.setAdapter(listAdapter);

        lsFood.setOnItemClickListener((parent, view, position, id) -> {
            ListAdapter.ViewHolder holder = (ListAdapter.ViewHolder) view.getTag();
            loadDetail(holder.id);
        });

        im1.setOnClickListener(v -> loadDetail(0));
        im2.setOnClickListener(v -> loadDetail(1));
        im3.setOnClickListener(v -> loadDetail(2));

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Food> searchFood = new ArrayList<>();
                String text = search.getText().toString().trim().toLowerCase();
                for(Food food: foods){
                    String sub = food.name.substring(0, text.length()).toLowerCase();
                    if(sub.compareTo(text) == 0) {
                        searchFood.add(food);
                    }
                }
                ListAdapter listAdapter = new ListAdapter(
                        MainActivity.this,
                        R.layout.item_grid,
                        searchFood.size(),
                        searchFood);
                lsFood.setAdapter(listAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    private void inItView(){
        lsFood = findViewById(R.id.lsFood);
        scroll = findViewById(R.id.scmain);
        im1 = findViewById(R.id.im1);
        im2 = findViewById(R.id.im2);
        im3 = findViewById(R.id.im3);
        tx1 = findViewById(R.id.tx1);
        tx2 = findViewById(R.id.tx2);
        tx3 = findViewById(R.id.tx3);
        search = findViewById(R.id.edSearch);

        lsFood.setOnTouchListener((v, event) -> {
            scroll.requestDisallowInterceptTouchEvent(true);
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_UP) {
                scroll.requestDisallowInterceptTouchEvent(false);
            }
            return false;
        });
    }

    private void loadDetail(int position){
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    public static ArrayList<Food> foods(){
        ArrayList<Food> foodList = new ArrayList<>();
        foodList.add(new Food(R.drawable.img,"Ch??? gi??",50000,7000,0 ,
                "https://www.foody.vn/ho-chi-minh/co-tuyet-cha-gio-cha-ram",
                "geo:10.776805698080334, 106.68051099924503?q=185/30 ???????ng 3 Th??ng 2, P. 11 , Qu???n 10, TP. HCM"));
        foodList.add(new Food(R.drawable.food2,"G?? n?????ng",60000,7000,1,
                "https://www.foody.vn/ho-chi-minh/chicken-kitchen-cac-mon-ga",
                "geo:10.774705111379955, 106.66892449714344?q=796/19B S?? V???n H???nh N???i D??i, P. 12, Qu???n 10, TP. HCM"));
        foodList.add(new Food(R.drawable.food3,"B??nh m?? th???t",25000,7000,2 ,
                "https://www.foody.vn/ho-chi-minh/banh-mi-thit-nuong-banh-mi-bien-tau-nguyen-trai",
                "geo:10.771003365726857, 106.69265992406467?q=?????u H???m 39 Nguy???n Tr??i, Qu???n 1, TP. HCM"));
        foodList.add(new Food(R.drawable.food4,"B??nh Taco",68000,7000,3,
                "https://www.foody.vn/ho-chi-minh/delicioso-taco",
                "geo:10.76708963119452, 106.66682069158989?q=533/5 Nguy???n Tri Ph????ng, P. 8, Qu???n 10, TP. HCM"));
        foodList.add(new Food(R.drawable.food5,"C??m tr???n",57000,7000 ,4,
                "https://www.foody.vn/ho-chi-minh/yre-com-tron-han-quoc",
                "geo:10.741802074016285, 106.71383053947136?q=23C Mai V??n V??nh, P. T??n Quy, Qu???n 7, TP. HCM"));
        foodList.add(new Food(R.drawable.food6,"Hamburger",68000,7000,5,
                "https://www.foody.vn/ho-chi-minh/mr-bmao-hamburger-hotdog-ton-dan",
                "geo:10.754278064740154, 106.70545287619144?q=384 T??n ?????n, P. 4, Qu???n 4, TP. HCM"));
        foodList.add(new Food(R.drawable.food7,"C??m chi??n chay",20000,7000 ,6,
                "https://www.foody.vn/ho-chi-minh/a-chay-cafe-com-chien-nui-xao-bo",
                "geo:10.757550544827478, 106.68090827569142?q=102A C?? X?? Ch??? Qu??n, Tr???n B??nh Tr???ng, Qu???n 5, TP. HCM"));
        foodList.add(new Food(R.drawable.food8,"???c g?? h???p",60800,7000,7,
                "https://www.foody.vn/ho-chi-minh/hong-ky-com-ga-xoi-mo",
                "geo:10.790165992312227, 106.75317243947197?q=189G Nguy???n Th??? ?????nh, P. An Ph??, Qu???n 2, TP. HCM"));
        foodList.add(new Food(R.drawable.food9,"Khoai t??y rau c???",50700,7000 ,8,
                "https://www.foody.vn/ho-chi-minh/robata-dining-an",
                "geo:10.781085010705409, 106.70522212597943?q=15C L?? Th??nh T??n, Qu???n 1, TP. HCM"));
        foodList.add(new Food(R.drawable.food10,"G?? s???t cay",68000,7000,9,
                "https://www.foody.vn/ho-chi-minh/chan-ga-sot-cay-shop-online",
                "geo:10.762687272542957, 106.59179216830752?q=Chung C?? T??n Mai, Qu???c L??? 1A, Qu???n B??nh T??n, TP. HCM"));
        return foodList;
    }
}