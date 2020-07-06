package com.rb.fs.service;

import com.rb.fs.dto.FileDto;
import com.rb.fs.entity.Files;
import com.rb.fs.entity.ProDeModel;
import com.rb.fs.util.Result;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


public interface FileService {
    /**
     * 添加文件以及文件和生成线，设备，型号的关系
     * @param fileDto
     * @return
     */
    void add(FileDto fileDto,String path);

    /**
     * 分页查询所有
     * @param page
     * @param limit
     * @return
     */
   // List selectAll(int page, int limit);

    /**
     * 查询总条数
     * @return
     */
   // int count();

    /**
     * 根据name查询
     * @param page
     * @param limit
     * @param fileNumber
     * @param versionNumber
     * @param filename
     * @return
     */
    //List<Files> selectByName(int page, int limit, String fileNumber, String versionNumber, String filename);

    /**
     * 条件查询总条数
     *
     * @param s
     * @param versionNumber
     * @param filename
     * @return
     */
    //int countBcyName(String s, String versionNumber, String filename);

    /**
     *
     * 删除
     * @param idss
     */
    Result delete(String idss);

    /**
     * 更改文件信息
     * @param id
     * @param fil_num
     * @param ver_num
     */
    void update(int id, String fil_num, String ver_num);

    /**
     * 文件与生产线，设备，型号建立关系
     */
    void save(int[] fileId, int[] dids, int[] berIds);

    /**
     * 根据id查询文件信息
     * @param fid
     * @return
     */
    Files findById(int fid);

    /**
     * 多条件查询
     * @param page
     * @param limit
     * @param fileNumber
     * @param versionNumber
     * @param filename
     * @return
     */
     Page<Files> getPageUpgradeScheduleView(int page, int limit, String filename,String fileNumber,String versionNumber);


}
