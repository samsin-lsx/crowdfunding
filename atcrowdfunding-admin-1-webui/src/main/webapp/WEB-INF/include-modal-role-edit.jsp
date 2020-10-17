<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="editModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<form role="form">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">尚筹网系统消息</h4>
				</div>
				<div class="modal-body">
					<input type="text" id="roleNameInputEdit" class="form-control" placeholder="请输入角色名称">
				</div>
				<div class="modal-footer">
					<button type="button" id="editModalBtn" class="btn btn-warning" style="outline: none;"><i class="glyphicon glyphicon-edit"></i> 更新</button>
					<button type="reset" class="btn btn-primary" style="outline: none;"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
				</div>
			</form>
		</div>
	</div>
</div>