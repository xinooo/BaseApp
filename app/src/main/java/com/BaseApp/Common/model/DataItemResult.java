package com.BaseApp.Common.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.BaseApp.Common.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 列表数据容器
 * 
 */
@SuppressLint("UseSparseArrays")
public class DataItemResult implements Parcelable {
	private List<DataItemDetail> dataList;
	private String itemUniqueKeyName = "";

	public DataItemDetail detailInfo;
	public int maxCount = 0;
	public int totalPage = 0;
	public int pageNo = 0;

	public String message = "";

	/**
	 * 构造函数，初始化列表容器，详细信息容器，数据适配器容器
	 */
	public DataItemResult() {
		this.dataList = new ArrayList();
		this.detailInfo = new DataItemDetail();
	}

	public DataItemResult Copy(){
		DataItemResult result = new DataItemResult();

		result.itemUniqueKeyName = itemUniqueKeyName;
		result.detailInfo = detailInfo.Copy();
		result.maxCount = maxCount;
		result.totalPage = totalPage;
		result.pageNo = pageNo;
		result.message = message;

		result.dataList = new ArrayList();

		for(DataItemDetail dataItem : dataList){
			result.dataList.add(dataItem.Copy());
		}

		return result;
	}
	
	/**
	 * 设置总页码
	 * @param page
	 */
	public void setTotalPage(int page){
		totalPage = page;
	}
	
	/**
	 * 设置当前页码
	 * @param page
	 */
	public void setPageNo(int page){
		pageNo = page;
	}

	/**
	 * 设置item的主键名
	 * 
	 * @param key
	 */
	public void setItemUniqueKeyName(String key) {
		itemUniqueKeyName = (null == key ? "" : key);
	}


	/**
	 * 获取item的唯一值
	 * 
	 * @param index
	 * @return int
	 */
	public int getItemID(int index) {
		if (null == itemUniqueKeyName || itemUniqueKeyName.length() < 1) {
			return index;
		}

		DataItemDetail item = getItem(index);

		if (item == null) {
			return index;
		}

		return item.getInt(itemUniqueKeyName);
	}

	/**
	 * 给定一个页码，计算当前总页数
	 * 
	 * @param pageSize
	 * @return int
	 */
	public int getTotalPage(int pageSize) {
		if (pageSize < 1 || maxCount < 1) {
			return 0;
		}

		return (int) Math.ceil((float) maxCount / pageSize);
	}

	/**
	 * 获取当前数据条数
	 * 
	 * @return int
	 */
	public int getDataCount() {
		if (dataList == null) {
			return 0;
		}
		return this.dataList.size();
	}

	/**
	 * 是否是一个有效的详细页数据
	 * 
	 * @return boolean
	 */
	public boolean isValidDetailData() {
		if (null == detailInfo) {
			return false;
		}

		if (detailInfo.getCount() < 1) {
			return false;
		}

		return true;

	}

	/**
	 * 是否是一个有效的列表数据
	 * 
	 * @return boolean
	 */
	public boolean isValidListData() {
		if (null == dataList) {
			return false; 
		}

		if (dataList.size() < 1) {
			return false;
		}

		return true;
	}

	/**
	 * 从键值对map数组中导入数据
	 * 
	 * @param mapList
	 * @return boolean
	 */
	public boolean importFromMapList(List<Map<String, String>> mapList) {
		boolean res = true;

		if (mapList == null || mapList.size() < 1) {
			return false;
		}

		for (int i = 0; i < mapList.size(); i++) {
			Map<String, String> map = mapList.get(i);
			DataItemDetail item = new DataItemDetail();

			if (item.importFromMap(map)) {
				res = dataListAddItem(item, -1) && res;
			}
		}

		if (maxCount < this.dataList.size()) {
			maxCount = this.dataList.size();
		}

		return res;
	}

	/**
	 * 添加一个对象
	 * 
	 * @param item
	 * @return boolean
	 */
	public boolean addItem(DataItemDetail item) {
		if (null == item) {
			return false;
		}

		if (dataListAddItem(item, -1)) {
			if (maxCount < this.dataList.size()) {
				maxCount = this.dataList.size();
			}

			return true;
		}

		return false;
	}
	
	/**
	 * 添加一个对象
	 * 
	 * @param item
	 * @param position
	 * @return boolean
	 */
	public boolean addItem(int position, DataItemDetail item) {
		if (null == item || position >= maxCount) {
			return false;
		}
		if (dataListAddItem(item, position)) {
			if (maxCount < this.dataList.size()) {
				maxCount = this.dataList.size();
			}

			return true;
		}
		return false;
	}
	
	/**
	 * 更新集合中的某条记录
	 * @param position
	 * @param item
	 * @return
	 */
	public boolean updateItem(int position, DataItemDetail item){
		if (null == item || position >= maxCount) {
			return false;
		}

		if(position < 0 || position >= this.dataList.size()){
			return this.dataList.add(item);
		} else {
			this.dataList.set(position, item);
			return true;
		}
		
	}

	/**
	 * 从另一个列表容器中创建当前容器
	 * 
	 * @param list
	 * @return boolean
	 */
	public boolean addItemList(List<DataItemDetail> list) {
		boolean res = true;

		if (list == null || list.size() < 1) {
			return false;
		}

		for (int i = 0; i < list.size(); i++) {
			res = dataListAddItem(list.get(i), -1) && res;
		}

		if (maxCount < this.dataList.size()) {
			maxCount = this.dataList.size();
		}

		return res;
	}

	/**
	 * 私有函数： 往当前列表指定位置添加一个item值（position小于0或者大于列表长度插入到list末端）
	 * 
	 * 带主键去重功能； 如遇重复主键值，则直接返回true
	 * 
	 * @param item
	 * @param position 希望插入到list中的位置，如果position值小于0或者大于等于列表长度那么直接添加到list的末尾位置
	 * @return boolean
	 */
	private boolean dataListAddItem(DataItemDetail item, int position) {
		if (item == null) {
			return false;
		}

		if (itemUniqueKeyName.length() > 0) {
			int maxCnt = this.dataList.size();
			String checkValue = item.getString(itemUniqueKeyName);

			for (int i = 0; i < maxCnt; i++) {
				String tempValue = this.dataList.get(i).getString(itemUniqueKeyName);

				if (tempValue.equals(checkValue)) {
					return true;
				}
			}
		}
		
		if(position < 0 || position >= this.dataList.size()){
			return this.dataList.add(item);
		} else {
			this.dataList.add(position, item);
			return true;
		}
	}

	/**
	 * 统计包含指定布尔键值对的 item 个数
	 * 
	 * @param key
	 * @param value
	 * @return int
	 */
	public int countItemsWithBooleanValue(String key, boolean value) {
		if (this.dataList.size() < 1) {
			return 0;
		}

		int result = 0;
		int startIndex = dataList.size() - 1;
		for (int i = startIndex; i > -1; i--) {
			DataItemDetail item = getItem(i);
			if (item == null) {
				continue;
			}

			if (item.getBoolean(key) == value) {
				result++;
			}
		}

		return result;
	}

	/**
	 * 
	 * 统计包含指定字符键值对的 item 个数
	 * 
	 * @param key
	 * @param value
	 * @return int
	 */
	public int countItemsWithStringValue(String key, String value) {
		if (this.dataList.size() < 1) {
			return 0;
		}

		int result = 0;
		int startIndex = dataList.size() - 1;
		for (int i = startIndex; i > -1; i--) {
			DataItemDetail item = getItem(i);
			if (item == null) {
				continue;
			}

			if (item.getString(key).equals(value)) {
				result++;
			}
		}

		return result;
	}

	/**
	 * 获取包含某个布尔键值对的item对应的主键列表，主键名为空时返回空
	 * 
	 * @param key
	 * @param value
	 * @return String
	 */
	public String getItemsIDWithBooleanValue(String key, boolean value) {
		if (itemUniqueKeyName.length() < 1) {
			return "";
		}

		if (this.dataList.size() < 1) {
			return "";
		}

		String result = "";
		int startIndex = dataList.size() - 1;
		for (int i = startIndex; i > -1; i--) {
			DataItemDetail item = getItem(i);
			if (item == null) {
				continue;
			}

			if (item.getBoolean(key) == value) {
				String ID = item.getString(itemUniqueKeyName);

				if (ID.length() > 0) {
					if (result.length() > 0) {
						result += ",";
					}
					result += ID;
				}
			}
		}

		return result;
	}

	/**
	 * 清除包含指定键值对的元素
	 * 
	 * @param key
	 * @param value
	 * @return boolean
	 */
	public boolean removeItemsWithStringValue(String key, String value) {
		if (this.dataList.size() < 1) {
			return true;
		}

		int startIndex = dataList.size() - 1;
		for (int i = startIndex; i > -1; i--) {
			DataItemDetail item = getItem(i);
			if (item == null) {
				// 2012-5-31 release version Log.e("DataItemResult", "Can not find index:" + i + " to remove!");
				return false;
			}

			if (item.hasKeyValue(key, value)) {
				if (null == this.removeByIndex(i)) {
					// 2012-5-31 release version Log.e("DataItemResult", "Remove item at index:" + i + " failed!");
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 把所有元素的指定键名的值都置成指定字符串值
	 * 
	 * @param key
	 * @param value
	 * @return boolean
	 */
	public boolean setAllItemsToStringValue(String key, String value) {
		if (this.dataList.size() < 1) {
			return true;
		}

		for (int i = 0; i < dataList.size(); i++) {
			DataItemDetail item = getItem(i);

			if (item == null) {
				// 2012-5-31 release version Log.e("DataItemResult", "Can not find index:" + i + " to change key-value paire!");
				return false;
			}

			if (!value.equals(item.setStringValue(key, value))) {
				// 2012-5-31 release version Log.e("DataItemResult", "change key-value paire for item at index:" + i + " failed!");
				return false;
			}
		}

		return true;
	}

	/**
	 * 把所有元素的指定键名的值都置成布尔值
	 * 
	 * @param key
	 * @param value
	 * @return boolean
	 */
	public boolean setAllItemsToBooleanValue(String key, Boolean value) {
		if (this.dataList.size() < 1) {
			return true;
		}

		for (int i = 0; i < dataList.size(); i++) {
			DataItemDetail item = getItem(i);

			if (item == null) {
				// 2012-5-31 release version Log.e("DataItemResult", "Can not find index:" + i + " to change key-value paire!");
				return false;
			}

			if (value != item.setBooleanValue(key, value)) {
				// 2012-5-31 release version Log.e("DataItemResult", "change key-value paire for item at index:" + i + " failed!");
				return false;
			}
		}

		return true;
	}

	/**
	 * 清除对应键名为true的元素
	 * 
	 * @param key
	 * @return boolean
	 */
	public boolean removeItemsWithTrueValue(String key) {
		if (this.dataList.size() < 1) {
			return true;
		}

		int startIndex = dataList.size() - 1;
		for (int i = startIndex; i > -1; i--) {
			DataItemDetail item = getItem(i);
			if (item == null) {
				// 2012-5-31 release version Log.e("DataItemResult", "Can not find index:" + i + " to remove!");
				return false;
			}

			if (item.getBoolean(key)) {
				if (null == this.removeByIndex(i)) {
					// 2012-5-31 release version Log.e("DataItemResult", "Remove item at index:" + i + " failed!");
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 清除所有元素，不包括数据适配器容器中的数据
	 * 
	 */
	public DataItemResult clear() {
		dataList.clear();
		detailInfo.clear();

		maxCount = 0;
		totalPage = 0;
		pageNo = 0;
		message = "";

		return this;
	}

	/**
	 * 往当前列表容器的后端追加另一个列表容器所有的数据
	 * 
	 * @param items
	 * @return boolean
	 */
	public boolean appendItems(DataItemResult items) {
		boolean res = true;

		if (null == items) {
			return false;
		}

		this.maxCount = items.maxCount;
		this.totalPage = items.totalPage;
		this.pageNo = items.pageNo;

		Map<String, String> itemInfo = items.detailInfo.getAllData();

		for (String key : itemInfo.keySet()) {
			String value = (String) itemInfo.get(key);
			detailInfo.setStringValue(key, value);
		}

		for (int i = 0; i < items.getDataCount(); i++) {
			res = dataListAddItem(items.getItem(i), -1) && res;
		}

		if (maxCount < this.dataList.size()) {
			maxCount = this.dataList.size();
		}

		return res;
	}

	/**
	 * 通过索引删除一个对象
	 * 
	 * @param index
	 * @return DataItemDetail
	 */
	public DataItemDetail removeByIndex(int index) {
		if(index < 0 || index >= dataList.size()){
			return null;
		}

		DataItemDetail item = this.dataList.remove(index);

		if (null != item) {
			maxCount--;
		}

		return item;
	}

	/**
	 * 删除一个对象
	 * 
	 * @param item
	 * @return boolean
	 */
	public boolean removeItem(DataItemDetail item) {
		if (this.dataList.remove(item)) {
			maxCount--;
			return true;
		}

		return false;
	}

	/**
	 * 通过索引取得一个对象
	 * 
	 * @param index
	 * @return DataItemDetail
	 */
	public DataItemDetail getItem(int index) {
		if (index < 0 || index >= this.dataList.size()) {
			return null;
		}

		return this.dataList.get(index);
	}
	
	

	public List<DataItemDetail> getDataList() {
		if (dataList == null) {
			dataList = new ArrayList();
		}
		return dataList;
	}

	public void setDataList(List<DataItemDetail> dataList) {
		this.dataList = dataList;
	}

	/**
	 * 调试用，输出列表中所有元素
	 * 
	 * @return void
	 */
	public void Dump() {
		Log.e("Dump", "==========  [basicInfo] ==========");
		Log.e("Dump", "  .maxCount: " + this.maxCount);
		Log.e("Dump", "  .totalPage: " + this.totalPage);
		Log.e("Dump", "  .pageNo: " + this.pageNo);
		Log.e("Dump", "  .errorMessage: " + this.message);

		if (this.detailInfo.getCount() > 0) {
			Log.e("Dump", "==========  [detailInfo] ==========");
			this.detailInfo.Dump();
		}

		if (this.dataList.size() > 0) {
			Log.e("Dump", "==========  [dataList] ==========");

			for (int i = 0; i < this.dataList.size(); i++) {
				Log.e("Dump", "----------  [item:" + (i + 1) + "] ----------");
				this.dataList.get(i).Dump();
			}
		}
	}

	/**
	 * 把对象数据转为字节数组
	 * 
	 * @return byte[]
	 */
	public byte[] toBytes() {
		byte[] result = null;

		try {
			Parcel out = Parcel.obtain();
			writeToParcel(out, 0);
			out.setDataPosition(0);
			result = out.marshall();
			out.recycle();
		} catch (Exception e) {
			Logger.e(e);
		}

		return result;
	}

	/**
	 * 把字节数据转换为 DataItemDetail 对象
	 * 
	 * @param bytesData 字节数据
	 * @return DataItemDetail 返回对象
	 */
	public static DataItemResult fromBytes(byte[] bytesData) {
		if (null == bytesData) {
			return new DataItemResult();
		}

		DataItemResult result = null;

		try {
			Parcel in = Parcel.obtain();

			in.unmarshall(bytesData, 0, bytesData.length);
			in.setDataPosition(0);

			result = DataItemResult.CREATOR.createFromParcel(in);

			in.recycle();
		} catch (Exception e) {
			Logger.e(e);
		}

		if (null != result) {
			return result;
		}

		return new DataItemResult();
	}

	/**
	 * 序列化描述符，默认为0
	 */
	@Override
	public int describeContents() {
		return 0;
	}
	
	/**
	 * 判断当前对象是否和另一个对象相同
	 * 
	 * @return boolean
	 */
	@Override
	public boolean equals(Object o) {
		if (null == o) {
			return false;
		}

		if (!(o instanceof DataItemResult)) {
			return false;
		}

		DataItemResult pO = (DataItemResult) o;

		// 判断message是否相等
		if (!pO.message.equals(message)) {
			return false;
		}

		// 判断maxCount是否相等
		if (pO.maxCount != maxCount) {
			return false;
		}
		
		// 判断totalPage是否相等
		if (pO.totalPage != totalPage) {
			return false;
		}
		
		// 判断pageNo是否相等
		if (pO.pageNo != pageNo) {
			return false;
		}

		// 判断detailInfo对象是否相等
		if (!pO.detailInfo.equals(detailInfo)) {
			return false;
		}
        //判断dataList对象是否相等
		if (pO.dataList.size() != this.dataList.size()) {
			return false;
		}
		int itemCount = this.getDataCount();
		for (int i = 0; i < itemCount; i++) {
			if (!pO.dataList.get(i).equals(this.dataList.get(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 对象序列化函数
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(message);
		dest.writeString(itemUniqueKeyName);

		dest.writeInt(maxCount);
		dest.writeInt(totalPage);
		dest.writeInt(pageNo);
		
		detailInfo.writeToParcel(dest, flags);

		int itemCount = this.getDataCount();

		dest.writeInt(itemCount);

		for (int i = 0; i < itemCount; i++) {
			this.getItem(i).writeToParcel(dest, flags);
		}

	}

	/**
	 * 构造容器
	 */
	public static final Creator<DataItemResult> CREATOR = new Creator<DataItemResult>() {
		public DataItemResult createFromParcel(Parcel in) {
			return new DataItemResult(in);
		}

		public DataItemResult[] newArray(int size) {
			return new DataItemResult[size];
		}
	};

	/**
	 * 对象反序列化函数
	 */
	public DataItemResult(Parcel in) {
		message = in.readString();
		itemUniqueKeyName = in.readString();

		maxCount = in.readInt();
		totalPage = in.readInt();
		pageNo = in.readInt();
		
		detailInfo = new DataItemDetail(in);
		dataList = new ArrayList();

		int itemCount = in.readInt();

		for (int i = 0; i < itemCount; i++) {
			DataItemDetail item = new DataItemDetail(in);
			this.addItem(item);
		}
	}
}