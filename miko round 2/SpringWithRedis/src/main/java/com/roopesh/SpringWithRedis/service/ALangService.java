package com.roopesh.SpringWithRedis.service;

import com.roopesh.SpringWithRedis.entity.ALangMVData;
import com.roopesh.SpringWithRedis.mapper.ALangMapper;
import com.roopesh.SpringWithRedis.repository.ALangAddRegistryRepository;
import com.roopesh.SpringWithRedis.repository.ALangMVDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ALangService {

    @Autowired
    ALangMVDataRepository mVDataRepo;

    @Autowired
    ALangAddRegistryRepository addRegistryRepo;

    @Autowired
    ALangMapper aLMapper;

    public ALangMVData  mvStatement(String statement) {
        try {

            String[] instructionParts = statement.replaceAll(",#", " ").split(" ");
            String register = instructionParts[1].trim();
            int value = Integer.parseInt(instructionParts[2].trim());
            return moveValueToDB(register, value);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ALangMVData addStatement(String statement) {
        try {

            String[] instructionParts = statement.split(" ");
            String[] targetRegisters = instructionParts[1].split(",");
            String register = targetRegisters[0].trim();
            int result = 0;
            for (String targetRegister : targetRegisters) {
                if (targetRegister.startsWith("REG")) {
                    ALangMVData regMVData = mVDataRepo.findByRegister(targetRegister.trim());
                    if (regMVData != null) {
                        result += regMVData.getCurrentValue();
                    }
                } else {
                    result += Integer.parseInt(targetRegister.trim());
                }
            }

            addRegistryRepo.save(aLMapper.mapToALangAddRegistry(instructionParts[1].trim()));

            return moveValueToDB(register, result);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ALangMVData moveValueToDB(String register, int currentValue) {

        ALangMVData prevMVData = mVDataRepo.findByRegister(register);
        if (prevMVData != null) {
            prevMVData.setPreviousValue(prevMVData.getCurrentValue());
            prevMVData.setCurrentValue(currentValue);
            prevMVData = mVDataRepo.save(prevMVData);
            return prevMVData;
        }
        prevMVData = mVDataRepo.save(aLMapper.mapToALangMVData(register, currentValue));

        return prevMVData;
    }

    public List<ALangMVData> showStatement(String statement) {
        try {

            String[] instructionParts = statement.split(" ");
            String register = instructionParts[1].trim();
            if (Objects.equals(register, "REG")) {
                return  mVDataRepo.findAll();
            } else {
                ALangMVData mVData = mVDataRepo.findByRegister(register);
                if (mVData != null) {
                    return Collections.singletonList(mVData);
                } else {
                    return Collections.emptyList();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<ALangMVData> executeProgram(String program) {
        try {

            List<ALangMVData> mVDataList = new ArrayList<>();
            String[] instructions = program.split("\n");
            for (String instruction : instructions) {

                instruction = instruction.trim();
                String[] instructionParts = instruction.split(" ");
                String operation = instructionParts[0].trim();

                switch (operation) {
                    case "MV":
                        mvStatement(instruction);
                        break;

                    case "ADD":
                        addStatement(instruction);
                        break;

                    case "SHOW":
                        mVDataList = showStatement(instruction);
                        break;

                    default:
                        throw new RuntimeException("'" + operation + "' is not a valid operation");
                }
            }
            return mVDataList;
        } catch (Exception e) {
            throw new RuntimeException("Error executing program: " + e.getMessage(), e);
        }
    }
}
