<#import "parts/common.ftl" as ht>

<@ht.page>


    <div class="nav nav-tabs" id="nav-tab" role="tablist">
        <a class="nav-item nav-link active" id="nav-subjects-tab" data-toggle="tab" href="#nav-subjects" role="tab"
           aria-controls="nav-subjects" aria-selected="true">Предложения</a>
        <a class="nav-item nav-link" id="nav-applicants-tab" data-toggle="tab" href="#nav-applicants" role="tab"
           aria-controls="nav-applicants" aria-selected="false">Претенденты</a>
    </div>

<div class="tab-content" id="nav-tabContent">
    <div class="tab-pane fade show active" id="nav-subjects" role="tabpanel" aria-labelledby="nav-subjects-tab">
        <#include "subjectintender.ftl" />
    </div>
    <div class="tab-pane fade" id="nav-applicants" role="tabpanel" aria-labelledby="nav-applicants-tab">
        <#include "applicantintender.ftl" />
    </div>
</div>

</@ht.page>