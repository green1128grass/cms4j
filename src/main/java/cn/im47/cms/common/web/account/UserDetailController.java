package cn.im47.cms.common.web.account;

import cn.im47.cms.common.entity.account.User;
import cn.im47.cms.common.service.account.GroupManager;
import cn.im47.cms.common.service.account.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 使用@ModelAttribute, 实现Struts2 Preparable二次绑定的效果。
 * 因为@ModelAttribute被默认执行, 而其他的action url中并没有${id}，所以需要独立出一个Controller.
 * <p/>
 * User: baitao.jibt (dreambt@gmail.com)
 * Date: 12-3-18
 * Time: 下午8:57
 */
@Controller
@RequestMapping(value = "/account/user/")
public class UserDetailController {

    private UserManager userManager;
    private GroupManager groupManager;

    private GroupListEditor groupListEditor;

    @InitBinder
    public void initBinder(WebDataBinder b) {
        b.registerCustomEditor(List.class, "groupList", groupListEditor);
    }

    @RequestMapping(value = "edit/{id}")
    public String updateForm(@ModelAttribute("user") User user, Model model) {
        if (null == user) {
            model.addAttribute("info", "该用户不存在，请刷新重试");
            return "redirect:/account/user/list";
        }
        model.addAttribute("user", user);
        model.addAttribute("allGroups", groupManager.getAllGroup());
        return "dashboard/account/user/edit";
    }

    @RequestMapping(value = "save/{id}")
    public String save(@ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (null == user) {
            redirectAttributes.addFlashAttribute("info", "该用户不存在，请刷新重试");
            return "redirect:/account/user/list";
        }
        if (bindingResult.hasErrors()) {
            return updateForm(user, redirectAttributes);
        }

        userManager.update(user);
        redirectAttributes.addFlashAttribute("info", "保存用户成功");
        return "redirect:/account/user/list";
    }

    @ModelAttribute("user")
    public User getAccount(@PathVariable("id") Long id) {
        return userManager.get(id);
    }

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Autowired
    public void setGroupManager(GroupManager groupManager) {
        this.groupManager = groupManager;
    }

    @Autowired
    public void setGroupListEditor(GroupListEditor groupListEditor) {
        this.groupListEditor = groupListEditor;
    }

}
