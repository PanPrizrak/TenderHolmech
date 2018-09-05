<#import "parts/common.ftl" as ht>
<#import "parts/login.ftl" as l>

<@ht.page>
<div class="mb-1">Add new user</div>
${message?ifExists}
<@l.login "/reg"  true/>

</@ht.page>