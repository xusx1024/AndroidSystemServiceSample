/*
 *  Copyright (C) 2017 The  sxxxxxxxxxu's  Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.shunwang.danmu;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.shunwang.danmu.view.BarrageRelativeLayout;
import java.util.LinkedList;

/**
 * Fun:简单弹幕原理实现
 * Created by sxx.xu on 5/18/2017.
 */

public class DanMuActivity extends Activity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_danmu);

    BarrageRelativeLayout mBarrageRelativeLayout =
        (BarrageRelativeLayout) findViewById(R.id.barrageView);

    String[] itemText = {
        "23333333333333333333", "6666666666666666666", "前方高能，请注意这不是演习，请注意这不是演习，请注意这不是演习！",
        "二营长，你他娘的意大利面呢？！", "我从未见过如此厚颜无耻之徒！", "妈的，智。。。。。。勇双全！", "亲亲抱抱举高高", "今天也要活力满满哦", "乐观的摸摸头",
        "伤心的像个三岁大的孩子", "表妹，莫慌，抱紧我", "逗", "爱国，敬业，和谐。。。。", "子谓公冶长：“可妻也，虽在缧绁之中，非其罪也！”以其子妻之。",
        "子谓南容：“邦有道不废；邦无道免于刑戮。”以其兄之子妻之。", "子谓子贱：“君子哉若人！鲁无君子者，斯焉取斯？”",
        "或曰：“雍也仁而不佞。”子曰：“焉用佞？御人以口给，屡憎于人。不知其仁，焉用佞？”", "子使漆雕开仕，对曰：“吾斯之未能信。”子说。",
        "子贡问曰：“赐也何如？”子曰：“女器也。”曰：“何器也？”曰：“瑚琏也。”", "子曰：“道不行，乘桴浮于海，从我者其由与？”子路闻之喜，子曰：“由也好勇过我，无所取材。”",
        "孟武伯问：“子路仁乎？”子曰：“不知也。”", "又问，子曰：“由也，千乘之国，可使治其赋也，不知其仁也。”“求也何如？”",
        "子曰：“求也，千室之邑、百乘之家，可使为之宰也，不知其仁也。”“赤也何如？”", "子曰：“赤也，束带立于朝，可使与宾客言也，不知其仁也。”",
        "子曰：“吾未见刚者。”或对曰：“申枨。”子曰：“枨也欲，焉得刚。”", "子贡曰：“我不欲人之加诸我也，吾亦欲无加诸人。”子曰：“赐也，非尔所及也。”",
    };
    LinkedList<String> texts = new LinkedList<String>();
    for (int i = 0; i < itemText.length; i++) {
      texts.add(itemText[i]);
    }
    mBarrageRelativeLayout.setBarrageTexts(texts);

    mBarrageRelativeLayout.show(BarrageRelativeLayout.RANDOM_SHOW);
  }
}
