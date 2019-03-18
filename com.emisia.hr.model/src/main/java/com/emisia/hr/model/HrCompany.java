package com.emisia.hr.model;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class HrCompany {

	private Map<UserRole, List<User>> _mapUser = new HashMap<UserRole, List<User>>();
	public HrCompany(List<User> list_user)
	{
		List<User> list;
		for(User user:list_user)
		{
			if(_mapUser.containsKey(user.getRole()))
			{
				list = _mapUser.get(user.getRole());
			}
			else
			{
				list = new ArrayList<User>();
			}
			list.add(user);
			_mapUser.put(user.getRole(), list);
		}
	}
}
