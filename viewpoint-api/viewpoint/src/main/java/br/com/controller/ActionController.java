package br.com.controller;

import br.com.HeaderParam;
import br.com.controller.request.ActionDTO;
import br.com.controller.request.ActionDataDTO;
import br.com.controller.response.ActionGroup;
import br.com.controller.response.UserGroup;
import br.com.model.entity.Action;
import br.com.service.ActionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/action", produces = MediaType.APPLICATION_JSON_VALUE)

public class ActionController {

    @Autowired
    private ActionService actionService;

    public ActionService getActionService() {
        return actionService;
    }

    public void setActionService(ActionService actionService) {
        this.actionService = actionService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Action create(@Validated @RequestBody ActionDTO dto) throws JsonProcessingException {
        ActionDataDTO action = dto.getAction();
        return getActionService().create(action);
    }

    @RequestMapping(value = "/deleteByGroup", method = RequestMethod.DELETE)
    public void deleteByGroup(@RequestParam String group, @RequestHeader(name = HeaderParam.AUTH_TOKEN) String token) {
        getActionService().deleteAllByGroup(group, token);
    }

    @RequestMapping(value = "/deleteByName", method = RequestMethod.DELETE)
    public void deleteByName(@RequestParam String name, @RequestHeader(name = HeaderParam.AUTH_TOKEN) String token) {
        getActionService().deleteAllByName(name, token);
    }

    @RequestMapping(value = "/findGroupsByUser", method = RequestMethod.GET)
    public UserGroup findAllGroupsByUser(@RequestHeader(name = HeaderParam.AUTH_TOKEN) String token) {
        return getActionService().findAllGroupsByUser(token);
    }

    @RequestMapping(value = "/findActionsByGroup", method = RequestMethod.GET)
    public List<ActionGroup> findAllActionsByGroup(@RequestHeader(name = HeaderParam.AUTH_TOKEN) String token,
                                                  @RequestParam(name = "group") String group) {
        return getActionService().findAllActionsByUser(token, group);
    }
}
