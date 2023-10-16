package br.com.mamadacosmica.todolist.util;

import java.beans.PropertyDescriptor;
import java.util.HashSet;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Util {

    public static void copyNonNullProprieties(Object x, Object y){
        BeanUtils.copyProperties(x, y, getNullProprietyNames(x));
    }

    public static String[] getNullProprietyNames(Object x){
        final BeanWrapper src = new BeanWrapperImpl(x);

        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        java.util.Set<String> emptyNames = new HashSet<>();

        for(PropertyDescriptor pd: pds){
            Object srcValue = src.getPropertyValue(pd.getName());
            if(srcValue == null){
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        
        return emptyNames.toArray(result);
   }
}
