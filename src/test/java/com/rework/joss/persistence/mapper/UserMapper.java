/**   
* @Package com.rework.joss.persistence.mapper 
*/
package com.rework.joss.persistence.mapper;

import java.util.List;

import com.rework.joss.persistence.entity.UserDTO;

/** 
 * <p>Description: </p>
 * @author zhangyang
 * <p>日期:2015年6月25日 上午9:14:06</p> 
 * @version V1.0 
 */
public interface UserMapper {

	public List<UserDTO> querylikeName(String name);
}
