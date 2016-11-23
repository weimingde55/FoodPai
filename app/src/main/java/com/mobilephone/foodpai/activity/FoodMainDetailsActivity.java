package com.mobilephone.foodpai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobilephone.foodpai.R;
import com.mobilephone.foodpai.adapter.MainDetailListViewAdapter;
import com.mobilephone.foodpai.bean.FoodMainDetailBean;
import com.mobilephone.foodpai.bean.UserBean;
import com.mobilephone.foodpai.bean.bmobbean.CollectBean;
import com.mobilephone.foodpai.myinterface.UnitOfHeat;
import com.mobilephone.foodpai.myview.MylistView;
import com.mobilephone.foodpai.util.DaoBmobUtil;
import com.mobilephone.foodpai.util.DownLoadImageUtil;
import com.mobilephone.foodpai.util.HttpUtil;
import com.mobilephone.foodpai.util.JsonUtil;
import com.mobilephone.foodpai.util.ThreadUtil;
import com.mobilephone.foodpai.util.UnitUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;


public class FoodMainDetailsActivity extends AppCompatActivity {

    private static final int GET_SEARCH_DETAILS = 10;
    private static final String TAG = "test";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ivGoodsPic)
    ImageView ivGoodsPic;
    @Bind(R.id.tvGoodsName)
    TextView tvGoodsName;
    @Bind(R.id.tvEnergy)
    TextView tvEnergy;
    @Bind(R.id.rbKilocalorie)
    RadioButton rbKilocalorie;
    @Bind(R.id.rbKilojoule)
    RadioButton rbKilojoule;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.tvUnit)
    TextView tvUnit;
    @Bind(R.id.tvIngredientCalory)
    TextView tvIngredientCalory;
    @Bind(R.id.tvLightsCalory)
    TextView tvLightsCalory;
    @Bind(R.id.tvIngredientProtein)
    TextView tvIngredientProtein;
    @Bind(R.id.tvLightsProtein)
    TextView tvLightsProtein;
    @Bind(R.id.tvIngredientFat)
    TextView tvIngredientFat;
    @Bind(R.id.tvLightsFat)
    TextView tvLightsFat;
    @Bind(R.id.tvInCarbohydrate)
    TextView tvInCarbohydrate;
    @Bind(R.id.tvLightsCarbohydrate)
    TextView tvLightsCarbohydrate;
    @Bind(R.id.tvInFiberDietary)
    TextView tvInFiberDietary;
    @Bind(R.id.tvLightsFiberDietary)
    TextView tvLightsFiberDietary;
    @Bind(R.id.tvGIvalue)
    TextView tvGIvalue;
    @Bind(R.id.tvGIgrade)
    TextView tvGIgrade;
    @Bind(R.id.tvGLvalue)
    TextView tvGLvalue;
    @Bind(R.id.tvGLgrade)
    TextView tvGLgrade;
    @Bind(R.id.ivCaloriesPic)
    ImageView ivCaloriesPic;
    @Bind(R.id.tvRide)
    TextView tvRide;
    @Bind(R.id.tvCompare)
    TextView tvCompare;
    @Bind(R.id.lvCalories)
    MylistView lvCalories;
    @Bind(R.id.tvLight)
    TextView tvLight;
    @Bind(R.id.tvSuggest)
    TextView tvSuggest;
    @Bind(R.id.tvAppraise)
    TextView tvAppraise;
    @Bind(R.id.rgSwitch)
    RadioGroup rgSwitch;
    @Bind(R.id.rlCompare)
    RelativeLayout rlCompare;
    @Bind(R.id.tvMultiple)
    TextView tvMultiple;
    @Bind(R.id.tvHead)
    TextView tvHead;



    private MainDetailListViewAdapter detailLvAdapter;
    private String calory;
    private String kilo;
    private String liCalory;
    private String inProtein;
    private String liProtein;
    private String inFat;
    private String liFat;
    private String inCarbohydrate;
    private String liCarbohydrate;
    private String inFiberDietary;
    private String liFiberDietary;
    private String _calory;

    private List<UnitOfHeat> unitOfHeats = new ArrayList<>();



    boolean isCollect = false;//判断是否已经收藏
    private String code;
    private Map<String, String> map = new HashMap<>();


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_SEARCH_DETAILS:
                    String json = (String) msg.obj;
                    if (json != null) {
                        FoodMainDetailBean detailBean = JsonUtil.parseFoodMainDetailBean(json);
                        if (detailBean != null) {
                            List<FoodMainDetailBean.UnitsBean> units = detailBean.getUnits();

                            //食物名称
                            name = detailBean.getName();
                            url = detailBean.getThumb_image_url();
                            imageUrl = detailBean.getLarge_image_url();
                            String fiber_dietary = detailBean.getFiber_dietary();//食物纤维
                            String calory =  detailBean.getCalory();//热量
                            String protein = detailBean.getProtein();//蛋白质
                            String carbohydrate = detailBean.getCarbohydrate();// 碳水化合物
                            String gi = detailBean.getGi();//Gi值
                            String gl = detailBean.getGl();//GL
                            String appraise = detailBean.getAppraise();//食物红绿灯
                            //初始化RadioButton
                            initRadioButton(units);
                            //刷新ListView
                            detailLvAdapter.notifyDataSetChanged();
                            //放置数据
                            placeData_i(detailBean);
                            placeData_ii(detailBean);
                            placeData_iii(detailBean);

                            Glide.with(FoodMainDetailsActivity.this).load(url).into(ivGoodsPic);
                            tvGoodsName.setText(name);
                            tvEnergy.setText(calory+"千卡");

                        }
                    }
                    break;
            }
        }
    };
    private String _kilo;
    private String name;
    private String url;
    private String imageUrl;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_main_details);

        ButterKnife.bind(this);

        //获取code
        Intent intent = getIntent();

        code = intent.getStringExtra("code");
        initFoodDetails(code);

        //初始化ToolBar
        initToolBar();

        //初始化ListiView
        initListView();

        onQuery();


    }

    /**
     *
     *
     * @param code
     */

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("详细信息");
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.setNavigationIcon(R.mipmap.ic_back_dark);
        menu = toolbar.getMenu();
    }

    /**
     * 为ToolBar绑定菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.food_main_detail_tool_bar_menu, menu);
        return true;
    }


    /**
     * 为ToolBar的菜单项设置点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.action_like:
                UserBean user = UserBean.getCurrentUser(UserBean.class);
                if (user!=null) {
                    //增加
                    if (isCollect == false) {
                        DaoBmobUtil.getInstance().onAdd(name,null,imageUrl,calory,code, new DaoBmobUtil.OnDaoAdd() {
                            @Override
                            public void onAdd(String s, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(FoodMainDetailsActivity.this, "收藏", Toast.LENGTH_SHORT).show();
                                    item.setIcon(R.mipmap.ic_news_keep_heighlight);
                                    onQuery();
                                }else {
                                    Toast.makeText(FoodMainDetailsActivity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        //删除
                    } else {
                        Log.e(TAG, "onOptionsItemSelected: ");
                        DaoBmobUtil.getInstance().onDelete(map,name, new DaoBmobUtil.OnDelete() {
                            @Override
                            public void onDelete(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(FoodMainDetailsActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                                    item.setIcon(R.mipmap.ic_news_keep_default);
                                    isCollect = false;
                                }else {
                                    Toast.makeText(FoodMainDetailsActivity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }

                break;
        }
        return true;
    }


    /**
     * 查询数据库
     * 获得objectId；
     */
    public void onQuery() {
        DaoBmobUtil.getInstance().onQuery(new DaoBmobUtil.OnDaoQuery() {
            @Override
            public void onQuery(List<CollectBean> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        String titlename = list.get(i).getTitle();
                        String objectId = list.get(i).getObjectId();
                        Log.e(TAG, "onQuery: "+titlename+"/"+objectId);
                        map.put(titlename, objectId); //获得objectId并存储在map中
                        if (titlename.equals(name)) {
                            //设置收藏图标和文字
                            Log.e(TAG, "onQueryequals: "+name);
                            menu.findItem(R.id.action_like).setIcon(R.mipmap.ic_news_keep_heighlight);
                            isCollect = true;
                        }
                    }
                }else {
                    Toast.makeText(FoodMainDetailsActivity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**
     * 根据FoodMainDetailBean.UnitsBean的数量，动态绘画RadioButton
     * 根据FoodMainDetailBean.UnitsBean的数量，动态绘画RadioButton
     * 根据FoodMainDetailBean.UnitsBean的数量，动态绘画RadioButton,并给unitOfHeats赋值
     * 每个物品的第一个RadioButton都是“每100克”，但是不在units中
     *
     * @param units
     */
    private void initRadioButton(List<FoodMainDetailBean.UnitsBean> units) {
        for (int i = 0; i <= units.size(); i++) {
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            layoutParams.setMargins(6, 5, 5, 6);
            RadioButton radioButton = new RadioButton(this);
            radioButton.setLayoutParams(layoutParams);
            if (i == 0) {
                radioButton.setText("每100克");
                radioButton.setChecked(true);
            } else {
                FoodMainDetailBean.UnitsBean unitsBean = units.get(i - 1);
                String unitName = unitsBean.getAmount() + unitsBean.getUnit();
                radioButton.setText(unitName);
                UnitOfHeat unitOfHeat = new UnitOfHeat(unitName, unitsBean.getWeight(), unitsBean.getCalory());
                unitOfHeats.add(unitOfHeat);
            }
            radioButton.setTextColor(getResources().getColor(R.color.mainGray));
            radioButton.setButtonDrawable(android.R.color.transparent);    //隐藏单选圆形按钮
            radioButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_main_detail_ii_radiobutton));
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            radioButton.setLines(1);
            radioButton.setId(i);
            radioGroup.addView(radioButton, layoutParams);
            radioGroup.invalidate();
        }
    }

    /**
     * 放置布局1的数据
     * @param detailBean
     */
    private void placeData_i(FoodMainDetailBean detailBean) {
        String name = detailBean.getName();
        calory = detailBean.getCalory();
        toolbar.setTitle(name);
        tvGoodsName.setText(name);
        DownLoadImageUtil.load(this, detailBean.getThumb_image_url(), R.mipmap.mq_ic_emoji_normal, R.mipmap.fail_img, ivGoodsPic);
        calory = detailBean.getCalory();
        rgSwitch.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbKilocalorie:
                        tvEnergy.setText(calory + "千卡");
                        break;
                    case R.id.rbKilojoule:
                        kilo = UnitUtil.conversionKilo(calory);
                        tvEnergy.setText(kilo + "千焦");
                        break;
                }
                int checked = radioGroup.getCheckedRadioButtonId();
                if (checked == 0) {
                    placeCaloryOrKilo(calory);
                } else {
                    placeCaloryOrKilo(_calory);
                }
            }
        });
    }

    /**
     * 放置布局2的数据
     *
     * @param detailBean
     */
    private void placeData_ii(final FoodMainDetailBean detailBean) {
        FoodMainDetailBean.IngredientBean ingredient = detailBean.getIngredient();
        final FoodMainDetailBean.LightsBean lights = detailBean.getLights();
        _calory = ingredient.getCalory();
        liCalory = lights.getCalory();
        inProtein = ingredient.getProtein();
        liProtein = lights.getProtein();
        inFat = ingredient.getFat();
        liFat = lights.getFat();
        inCarbohydrate = ingredient.getCarbohydrate();
        liCarbohydrate = lights.getCarbohydrate();
        inFiberDietary = ingredient.getFiber_dietary();
        liFiberDietary = lights.getFiber_dietary();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId > 0) {
                    List<FoodMainDetailBean.UnitsBean> units = detailBean.getUnits();
                    String calory_unit = units.get(checkedId - 1).getCalory();
                    placeCaloryOrKilo(calory_unit);
                    inProtein = UnitUtil.conversionUnit(_calory, calory_unit, inProtein);
                    inFat = UnitUtil.conversionUnit(_calory, calory_unit, inFat);
                    inCarbohydrate = UnitUtil.conversionUnit(_calory, calory_unit, inCarbohydrate);
                    inFiberDietary = UnitUtil.conversionUnit(_calory, calory_unit, inFiberDietary);
                    tvUnit.setText(units.get(checkedId - 1).getAmount() + units.get(checkedId - 1).getUnit());
                } else {
                    tvUnit.setText("每100克");
                    placeCaloryOrKilo(_calory);
                }
                placeData(detailBean, lights);
            }
        });
        placeData(detailBean, lights);
        placeCaloryOrKilo(_calory);
    }

    private void placeData(FoodMainDetailBean detailBean, FoodMainDetailBean.LightsBean lights) {
        tvLightsCalory.setText(liCalory);
        tvIngredientProtein.setText(inProtein.length() == 0 ? "---" : inProtein + "克");
        tvLightsProtein.setText(liProtein);
        tvIngredientFat.setText(inFat.length() == 0 ? "---" : inFat + "克");
        tvLightsFat.setText(liFat);
        tvInCarbohydrate.setText(inCarbohydrate.length() == 0 ? "---" : inCarbohydrate + "克");
        tvLightsCarbohydrate.setText(liCarbohydrate);
        tvInFiberDietary.setText(inFiberDietary.length() == 0 ? "---" : inFiberDietary + "克");
        tvLightsFiberDietary.setText(liFiberDietary);
        tvGIvalue.setText(detailBean.getGi());
        tvGLvalue.setText(detailBean.getGl());
        tvGIgrade.setText(lights.getGi());
        tvGLgrade.setText(lights.getGl());
    }

    private void placeCaloryOrKilo(String calory) {
        if (rbKilocalorie.isChecked()) {
            tvIngredientCalory.setText(calory + "千卡");
        } else {
            String kilo = UnitUtil.conversionKilo(calory);
            tvIngredientCalory.setText(kilo + "千焦");
        }
    }

    /**
     * 初始化布局3中的ListView
     */
    public void initListView() {
        detailLvAdapter = new MainDetailListViewAdapter(this, unitOfHeats);
        lvCalories.setAdapter(detailLvAdapter);
    }

    /**
     * 放置布局3中的数据
     *
     * @param detailBean
     */
    public void placeData_iii(FoodMainDetailBean detailBean) {
        String appraise = detailBean.getAppraise();
        FoodMainDetailBean.CompareBean compare = detailBean.getCompare();
        Method[] methods = compare.getClass().getMethods();
        tvAppraise.setText(appraise);
        int health_light = detailBean.getHealth_light();
        switch (health_light) {
            case 1:
                tvLight.setBackgroundResource(R.mipmap.ic_food_light_green);
                tvSuggest.setText("推荐");
                tvSuggest.setTextColor(getResources().getColor(R.color.light_green));
                break;
            case 2:
                tvLight.setBackgroundResource(R.mipmap.ic_food_light_yellow);
                tvSuggest.setText("适量");
                tvSuggest.setTextColor(getResources().getColor(R.color.light_yellow));
                break;
            case 3:
                tvLight.setBackgroundResource(R.mipmap.ic_food_light_red);
                tvSuggest.setText("少吃");
                tvSuggest.setTextColor(getResources().getColor(R.color.light_red));
                break;
        }
        if(detailBean.getUnits() == null||detailBean.getUnits().size() == 0){
            tvHead.setVisibility(View.GONE);
        }

        if (compare.getAmount1() == null){
            rlCompare.setVisibility(View.GONE);
            ivCaloriesPic.setVisibility(View.GONE);
            tvMultiple.setVisibility(View.GONE);
            tvCompare.setVisibility(View.GONE);
        }else {
            DownLoadImageUtil.load(this,compare.getTarget_image_url(),R.mipmap.mq_ic_emoji_normal,R.mipmap.fail_img,ivCaloriesPic);
            tvMultiple.setText(compare.getAmount1());
            tvCompare.setText(compare.getAmount0()+compare.getUnit0()+detailBean.getName()+" ≈ "+compare.getAmount1()+compare.getUnit1()+compare.getTarget_name());
        }

    }


    /**
     * 初始化FoodDetail的数据
     *
     * @param code
     */

    private void initFoodDetails(final String code) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                String foodMainDetailJson = HttpUtil.getFoodMainDetailJson(FoodMainDetailsActivity.this, code);
                if (foodMainDetailJson != null) {
                    Message msg = handler.obtainMessage();
                    msg.what = GET_SEARCH_DETAILS;
                    msg.obj = foodMainDetailJson;
                    handler.sendMessage(msg);
                }
            }
        });


    }
}
