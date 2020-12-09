package org.mineok.service.impl;

import org.mineok.dao.DirectorDao;
import org.mineok.entity.Director;
import org.mineok.service.DirectorService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.Query;



@Service("directorService")
public class DirectorServiceImpl extends ServiceImpl<DirectorDao, Director> implements DirectorService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Director> page = this.page(
                new Query<Director>().getPage(params),
                new QueryWrapper<Director>()
        );

        return new PageUtils(page);
    }

}