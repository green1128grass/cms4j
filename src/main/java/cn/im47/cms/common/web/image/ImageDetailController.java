package cn.im47.cms.common.web.image;

import cn.im47.cms.common.entity.image.Image;
import cn.im47.cms.common.service.image.ImageManager;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * image指定id的控制器
 * User: pengfei.dongpf@gmail.com
 * Date: 12-4-3
 * Time: 下午1:01
 */
@Controller
@RequestMapping(value = "/gallery/")
public class ImageDetailController {

    private ImageManager imageManager;

    /**
     * 打开修改image的页面
     *
     * @param image
     * @param model
     * @return
     */
    @RequiresPermissions("gallery:edit")
    @RequestMapping(value = "edit/{id}")
    public String edit(@Valid @ModelAttribute Image image, Model model) {
        if (null == image) {
            model.addAttribute("error", "该图片不存在，请刷新重试");
            return "redirect:/image/listAll";
        }
        model.addAttribute("image", image);
        return "dashboard/image/edit";
    }

    /**
     * 保存编号为id的image
     *
     * @param file
     * @param request
     * @param id
     * @param image
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("gallery:save")
    @RequestMapping(value = "save/{id}")
    public String save(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,
                       @PathVariable Long id, @Valid @ModelAttribute("image") Image image, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || null == image) {
            redirectAttributes.addFlashAttribute("error", "该图片不存在，请刷新重试");
            return "redirect:/image/listAll";
        }
        if (imageManager.update(file, request, image) <= 0) {
            redirectAttributes.addFlashAttribute("error", "修改图片信息失败");
        } else {
            redirectAttributes.addFlashAttribute("info", "修改" + id + "图片信息成功");
        }
        return "redirect:/gallery/listAll";
    }

    @ModelAttribute("image")
    public Image getImage(@PathVariable Long id) {
        return imageManager.get(id);
    }

    @Autowired
    public void setImageManager(ImageManager imageManager) {
        this.imageManager = imageManager;
    }

}
