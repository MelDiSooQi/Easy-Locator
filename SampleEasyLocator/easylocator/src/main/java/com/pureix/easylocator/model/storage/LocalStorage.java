package com.pureix.easylocator.model.storage;

import android.content.Context;
import android.preference.PreferenceManager;

import com.pureix.easylocator.model.utility.StringUtilitiesHandler;

import java.util.Set;

public class LocalStorage
{
//===================================================================================================================
//	public static String PREFERENCE_TYPE_BOOLEAN	= "Boolean";
//	public static String PREFERENCE_TYPE_FLOAT		= "Float";
//	public static String PREFERENCE_TYPE_INT		= "Int";
//	public static String PREFERENCE_TYPE_LONG		= "Long";
//	public static String PREFERENCE_TYPE_STRING		= "String";
//	public static String PREFERENCE_TYPE_STRINGSET	= "StringSet";

	public static boolean setPreference(Context context, String key, Object value)
	{
		//o.getClass().getName();
		//o.getClass().getSimpleName()
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key+"type", value.getClass().getName()).commit();

		if(value.getClass() == Boolean.class)
		{
			return PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean	(key, (Boolean) value).commit();
		}
		else if(value.getClass() == Float.class)
		{
			return PreferenceManager.getDefaultSharedPreferences(context).edit().putFloat(key, (Float) value).commit();
		}
		else if(value.getClass() == Integer.class)
		{
			return PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(key, (Integer) value).commit();
		}
		else if(value.getClass() == Long.class)
		{
			return PreferenceManager.getDefaultSharedPreferences(context).edit().putLong(key, (Long) value).commit();
		}
		else if(value.getClass() == String.class)
		{
			return PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, (String) value).commit();
		}
		else if(value.getClass() == Set.class)
		{
			return PreferenceManager.getDefaultSharedPreferences(context).edit().putStringSet(key, (Set<String>) value).commit();
		}
		else
		{
			return false;
		}
	}
//888888888888888888888888888888888888888888888888888888888
	public static Object getPreference(Context context, String key)
	{
		String type = PreferenceManager.getDefaultSharedPreferences(context).getString(key+"type", null);

		if(type != null)
		{
			if(StringUtilitiesHandler.StringEqual(type, Boolean.class.getName()))
			{
				return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false);
			}
			else if(StringUtilitiesHandler.StringEqual(type, Float.class.getName()))
			{
				return PreferenceManager.getDefaultSharedPreferences(context).getFloat(key, 0);
			}
			else if(StringUtilitiesHandler.StringEqual(type, Integer.class.getName()))
			{
				return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, 0);
			}
			else if(StringUtilitiesHandler.StringEqual(type, Long.class.getName()))
			{
				return PreferenceManager.getDefaultSharedPreferences(context).getLong(key, 0);
			}
			else if(StringUtilitiesHandler.StringEqual(type, String.class.getName()))
			{
				//null == "defaultStringIfNothingFound"
				return PreferenceManager.getDefaultSharedPreferences(context).getString(key, null);
			}
			else if(StringUtilitiesHandler.StringEqual(type, Set.class.getName()))
			{
				return PreferenceManager.getDefaultSharedPreferences(context).getStringSet(key, null);
			}
			else
			{
				return null;
			}
		}else
		{
			return null;
		}

	}
//888888888888888888888888888888888888888888888888888888888
	public static Object getPreference(Context context, String key, Object defaultValue)
	{
		String type = PreferenceManager.getDefaultSharedPreferences(context).getString(key+"type", null);

		if(type != null)
		{
			if(StringUtilitiesHandler.StringEqual(type, Boolean.class.getName()))
			{
				return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, (Boolean) defaultValue);
			}
			else if(StringUtilitiesHandler.StringEqual(type, Float.class.getName()))
			{
				return PreferenceManager.getDefaultSharedPreferences(context).getFloat(key, (Float) defaultValue);
			}
			else if(StringUtilitiesHandler.StringEqual(type, Integer.class.getName()))
			{
				return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, (Integer) defaultValue);
			}
			else if(StringUtilitiesHandler.StringEqual(type, Long.class.getName()))
			{
				return PreferenceManager.getDefaultSharedPreferences(context).getLong(key, (Long) defaultValue);
			}
			else if(StringUtilitiesHandler.StringEqual(type, String.class.getName()))
			{
				//null == "defaultStringIfNothingFound"
				return PreferenceManager.getDefaultSharedPreferences(context).getString(key, (String) defaultValue);
			}
			else if(StringUtilitiesHandler.StringEqual(type, Set.class.getName()))
			{
				return PreferenceManager.getDefaultSharedPreferences(context).getStringSet(key, (Set<String>) defaultValue);
			}
			else
			{
				return null;
			}
		}else
		{
//			if(defaultValue.getClass() == Boolean.class)
//			{
//				return (Boolean) defaultValue;
//			}
//			else if(defaultValue.getClass() == Float.class)
//			{
//				 return (Float) defaultValue;
//			}
//			else if(defaultValue.getClass() == Integer.class)
//			{
//				return (Integer) defaultValue;
//			}
//			else if(defaultValue.getClass() == Long.class)
//			{
//				return (Long) defaultValue;
//			}
//			else if(defaultValue.getClass() == String.class)
//			{
//				return (String) defaultValue;
//			}
//			else if(defaultValue.getClass() == Set.class)
//			{
//				return (Set<String>) defaultValue;
//			}else
//			{
				return defaultValue;
//			}
		}
	}
//888888888888888888888888888888888888888888888888888888888
	public static boolean removePreference(Context context, String key)
	{
		return PreferenceManager.getDefaultSharedPreferences(context).edit().remove(key).commit();
	}
//888888888888888888888888888888888888888888888888888888888
	public static boolean ClearPreference(Context context)
	{
		return PreferenceManager.getDefaultSharedPreferences(context).edit().clear().commit();
	}
//888888888888888888888888888888888888888888888888888888888
	public static boolean ClearPreferenceByApply(Context context)
	{
		PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply();
		return true;
	}
//===================================================================================================================
}
