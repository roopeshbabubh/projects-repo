package com.roopesh.SpringWithRedis.mapper;

import com.roopesh.SpringWithRedis.entity.ALangAddRegistry;
import com.roopesh.SpringWithRedis.entity.ALangMVData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ALangMapper {

    public ALangMVData mapToALangMVData(String register, int currentValue) {

        ALangMVData mVData = new ALangMVData();
        mVData.setRegister(register);
        mVData.setCurrentValue(currentValue);
        System.out.println(mVData);
        return mVData;
    }

    public ALangAddRegistry mapToALangAddRegistry(String addStatement) {

        ALangAddRegistry addRegistry = new ALangAddRegistry();
        addRegistry.setAddStatement(addStatement);
        return addRegistry;
    }

}
