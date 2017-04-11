package com.spreadwin.sqlit.demo.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.spreadwin.sqlit.demo.bean.Subscriber;
import com.spreadwin.sqlit.R;

public class MainActivity extends Activity implements OnClickListener {

	private Subscriber mSub;

	private Button btnInit;
	private Button btnQuery;
	private Button btnReplace;
	private TextView tvString;
	private EditText etName;
	private EditText etAge;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tvString = (TextView) findViewById(R.id.tv_string);

		btnInit = (Button) findViewById(R.id.btn_init);
		btnQuery = (Button) findViewById(R.id.query);
		btnReplace = (Button) findViewById(R.id.replace);

		etName = (EditText) findViewById(R.id.et_name);
		etAge = (EditText) findViewById(R.id.et_age);

		btnInit.setOnClickListener(this);
		btnQuery.setOnClickListener(this);
		btnReplace.setOnClickListener(this);

	}

	private void save() {
		if (etName.getText().toString().trim().equals("")
				|| etAge.getText().toString().trim().equalsIgnoreCase("")) {
			Toast.makeText(MainActivity.this, "年龄和姓名不能为空", Toast.LENGTH_SHORT)
					.show();
		} else {
			mSub = new Subscriber();
			mSub.setName(etName.getText().toString().trim());
			mSub.setAge(Integer.parseInt(etAge.getText().toString().trim()));
			mSub.save();
		}
	}

	private void query() {
		// 查询全部
		if (etAge.getText().toString().trim().equals("")
				&& etName.getText().toString().trim().equals("")) {
			List<Subscriber> list1 = new Select().from(Subscriber.class)
					.execute();
			ergodic(list1, "list1");

			// 根据年龄查询
		} else if (etAge.getText().toString().trim() != ""
				&& etName.getText().toString().trim().equals("")) {
			List<Subscriber> list2 = new Select().from(Subscriber.class)
					.where("age=?", Integer.parseInt(etAge.getText().toString().trim())).execute();
			ergodic(list2, "list2");
			Toast.makeText(MainActivity.this, "根据年龄查询", Toast.LENGTH_SHORT)
					.show();
			// 根据姓名查询
		} else if (etAge.getText().toString().trim().equals("")
				&& etName.getText().toString().trim() != "") {
			List<Subscriber> list12 = new Select().from(Subscriber.class)
					.where("name=?", etName.getText().toString().trim())
					.execute();
			ergodic(list12, "list12");
			// 根据年龄，姓名查询
		} else if (etAge.getText().toString().trim() != ""
				&& etName.getText().toString().trim() != "") {
			List<Subscriber> list2 = new Select()
					.from(Subscriber.class)
					.where("age=? and name=?",
							Integer.parseInt(etAge.getText().toString().trim()),
							etName.getText().toString().trim()).execute();
			ergodic(list2, "list2");
		}
	}

	private void replace() {
		List<Subscriber> list3 = new Select().from(Subscriber.class)
				.where("age=?", 12).execute();
		for (int i = 0; i < list3.size(); i++) {
			if (etAge.getText().toString().trim().equals("")) {
				list3.get(i).setAge(100);
				list3.get(i).save();
			} else {
				list3.get(i).setAge(
						Integer.parseInt(etAge.getText().toString().trim()));
				list3.get(i).save();
			}
		}

	}

	private void ergodic(List<Subscriber> list, String str) {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(str + "_" + i + ":" + list.get(i).toString());
			sb.append(list.get(i).toString());
		}
		if(sb.toString().equals("")){
			tvString.setText("没有数据");
		}else{
			tvString.setText(sb.toString());
		}
		sb = null;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_init:
			save();
			etAge.setText("");
			etName.setText("");
			tvString.setText("");
			break;
		case R.id.query:
			query();
			break;
		case R.id.replace:
			replace();
			tvString.setText("");
			break;
		default:
			break;
		}
	}

}
