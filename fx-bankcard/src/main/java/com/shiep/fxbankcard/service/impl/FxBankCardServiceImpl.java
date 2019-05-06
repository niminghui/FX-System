package com.shiep.fxbankcard.service.impl;

import com.shiep.fxbankcard.entity.FxBankCard;
import com.shiep.fxbankcard.repository.FxBankCardRepository;
import com.shiep.fxbankcard.service.IFxBankCardService;
import com.shiep.fxbankcard.utils.BankCardIdGeneratorUtils;
import com.shiep.fxbankcard.utils.UuidTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * @author: 倪明辉
 * @date: 2019/4/15 16:21
 * @description: IFxBankCardService的实现类
 */
@Service
public class FxBankCardServiceImpl implements IFxBankCardService {
    @Autowired
    private FxBankCardRepository bankCardRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<FxBankCard> findAll() {
        return bankCardRepository.findAll();
    }

    @Override
    public FxBankCard findByUserID(String userID) {
        return bankCardRepository.findByUserID(userID);
    }

    @Override
    public List<FxBankCard> findByStatus(Integer status) {
        return bankCardRepository.findByStatus(status);
    }

    @Override
    public List<FxBankCard> findByCreatedTimeAfter(Timestamp time) {
        return bankCardRepository.findByCreatedTimeAfter(time);
    }

    @Override
    public List<FxBankCard> findByCreatedTimeBefore(Timestamp time) {
        return bankCardRepository.findByCreatedTimeBefore(time);
    }

    @Override
    public List<FxBankCard> findByCreatedTimeBetween(Timestamp beginTime, Timestamp endTime) {
        return bankCardRepository.findByCreatedTimeBetween(beginTime, endTime);
    }

    @Override
    public FxBankCard findByBankCardId(String bankCardId) {
        Optional<FxBankCard> bankCard = bankCardRepository.findById(bankCardId);
        return bankCard.isPresent() ? bankCard.get() : null;
    }

    @Override
    public FxBankCard createInitBankCard(String createdPlace, String userID) {
        FxBankCard bankCard = new FxBankCard();
        bankCard.setId(BankCardIdGeneratorUtils.getBankCardID());
        String password = UuidTools.get8BitUUID();
        bankCard.setPassword(passwordEncoder.encode(password));
        bankCard.setUserID(userID);
        // 银行卡状态未激活
        bankCard.setStatus(FxBankCard.INACTIVE);
        bankCard.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        bankCard.setCreatedPlace(createdPlace);
        FxBankCard save = bankCardRepository.save(bankCard);
        if (save != null) {
            // 将未加密的密码返回
            FxBankCard result = new FxBankCard();
            result.setId(save.getId());
            result.setPassword(password);
            result.setStatus(save.getStatus());
            result.setCreatedTime(save.getCreatedTime());
            result.setCreatedPlace(save.getCreatedPlace());
            result.setUserID(save.getUserID());
            return result;
        }
        return null;
    }

    @Override
    public Boolean activeBankCard(String bankCardId) {
        FxBankCard bankCard = findByBankCardId(bankCardId);
        if (bankCard != null) {
            bankCard.setStatus(FxBankCard.ACTIVATED);
            if (bankCardRepository.save(bankCard) != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FxBankCard freezeBankCard(String bankCardId) {
        FxBankCard bankCard = findByBankCardId(bankCardId);
        if (bankCard != null) {
            bankCard.setStatus(FxBankCard.FREEZE);
            return bankCardRepository.save(bankCard);
        }
        return null;
    }

    @Override
    public FxBankCard unFreezeBankCard(String bankCardId) {
        FxBankCard bankCard = findByBankCardId(bankCardId);
        if (bankCard != null) {
            bankCard.setStatus(FxBankCard.ACTIVATED);
            return bankCardRepository.save(bankCard);
        }
        return null;
    }

    @Override
    public FxBankCard updatePassword(String bankCardId, String oldPassword, String newPassword) {
        FxBankCard bankCard = findByBankCardId(bankCardId);
        if (bankCard != null && passwordEncoder.matches(oldPassword,bankCard.getPassword())) {
            bankCard.setPassword(passwordEncoder.encode(newPassword));
            return bankCardRepository.save(bankCard);
        }
        return null;
    }

    @Override
    public String resetInitPassword(String bankCardId) {
        FxBankCard bankCard = findByBankCardId(bankCardId);
        if (bankCard != null) {
            String password = UuidTools.get8BitUUID();
            bankCard.setPassword(passwordEncoder.encode(password));
            FxBankCard save = bankCardRepository.save(bankCard);
            if (save != null) {
               return password;
            }
        }
        return null;
    }

    @Override
    public FxBankCard deleteBankCard(String bankCardId) {
        FxBankCard bankCard = findByBankCardId(bankCardId);
        if (bankCard != null) {
            bankCard.setStatus(FxBankCard.CANCELLATION);
            return bankCardRepository.save(bankCard);
        }
        return null;
    }
}
