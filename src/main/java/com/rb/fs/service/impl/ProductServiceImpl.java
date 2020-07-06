package com.rb.fs.service.impl;

import com.rb.fs.dao.*;
import com.rb.fs.entity.Device;
import com.rb.fs.entity.ProductLine;
import com.rb.fs.service.ModelService;
import com.rb.fs.service.ProDeModelService;
import com.rb.fs.service.ProductLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
@Transactional
@Service
public class ProductServiceImpl  implements ProductLineService {

    @Autowired
    private ProductLineDao productLineDao;
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private ModelDao modelDao;
    @Autowired
    private ProDeModelDao proDeModelDao;
    @Autowired
    private DistributeDao distributeDao;

    /**
     * 添加生产线
     * @param names
     */
    @Override
    public void addLine(String names) {
        String[] nameArry=names.split(",");
        for(int i=0;i<nameArry.length;i++){
            if(!nameArry[i].equals("")||nameArry[i]!=null) {
                ProductLine productLine = new ProductLine(nameArry[i], new Date());
                productLineDao.save(productLine);
            }
        }
    }

    /**
     * 查询所有生产线
     * @return
     * @param page
     * @param limit
     */
    @Override
    public List<ProductLine> selectLine(int page, int limit) {
        page=(page-1)*limit;
        return  productLineDao.findAllByPage(page,limit);
    }

    /**
     * 删除生产线
     * @param ids
     */
    @Override
    public void delete(String ids) {
        String[] idss=ids.split(",");
        for (int i = 0; i < idss.length; i++) {
            if (idss[i] != null && !idss[i].equals("")) {
                int id=Integer.parseInt(idss[i]);
                productLineDao.deleteById(id);
                //删除所有设备
                deviceDao.deleteByPid(id);
                //删除生产线下所有型号
                modelDao.deleteByPid(id);
                //ProductLine productLine=productLineDao.findById(id);
                //设置分配记录失效
                distributeDao.updateEffectByPid(1,0,id);
                //设置这个生产线下的文件失效
                proDeModelDao.updateByPid(id,1,0);
            }
        }

    }

    /**
     * 修改
     * @param id
     * @param name
     */
    public void update(int id, String name){
        productLineDao.updateById(id,name,new Date());
    }

    /**
     * 根据生产线查询出所有设备
     * @param prolies
     * @return
     */
    @Override
    public List<Device> getDeviceByProLine(Integer prolies) {
        return deviceDao.getDeviceByProLine(prolies);
    }

    @Override
    public ProductLine findById(int id) {
      return productLineDao.findById(id);
    }

    @Override
    public List<ProductLine> selectByName(int page, int limit, String name) {
        page=(page-1)*limit;
        return productLineDao.findByNameLikePage(page,limit,"%"+name+"%");
    }

    /**
     * 查询总条数
     * @return
     */
    @Override
    public int count() {
        return (int) productLineDao.count();
    }

    /**
     * 根据查询条件查询总条数
     * @param name
     * @return
     */
    @Override
    public int countByName(String name) {
      return   productLineDao.countByName("%"+name+"%");
    }

    /**
     * 不分页查询所有
     * @return
     */
    @Override
    public List<ProductLine> selectAll() {
        return (List<ProductLine>) productLineDao.findAll();
    }

    /**
     * 根据名称模糊查询
     * @param proline
     * @return
     */
    @Override
    public List<ProductLine> findByLikeName(String proline) {
        return productLineDao.findByNameLike("%"+proline+"%");
    }


}
