package com.suave.newworld.beans.db;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Suave
 * @since 2019-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Follows extends Model<Follows> {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer followId;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
