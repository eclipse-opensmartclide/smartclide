# Summary
The Context Extractor allows to monitor and extract the context of the situation under which a product/machine/component is being used, taking into account the human operator. 
A methodology is guiding the process on how to define a Context Model for extraction of context.
Keywords: Context Sensitivity

# Description
These core services are used for extraction of context during daily product/machine/component use operation, and to provide the extracted context to the downstream services. The services to extract the context could be extended to include the observation of the human operator behaviour, i.e. to take his activities into account when identifying current context.
Baseline for identifying current context is the context model. The context model describes circumstances under which a product/machine/component is currently used, and by this allows adjustments to dynamically meet specific needs of the users of the products/machines/components. The context model is set of concepts and their relations which describe circumstances under which the product is currently used.

# Functionalities
* Monitoring of daily product/machine/component use – monitoring of use of product/machine/components and possibly of machine operator behaviour.
* Extraction of product/machine/component use context – given the use of the product/machine/components, their context will be extracted. It takes the monitored activities, the context models (modelling the circumstances under which the product/machine/components are used) and the past contexts to infer upon the context of the product/machine/components.
* Modelling of Context Models – Context Models can be modelled in different abstraction levels, from general, to sector, to company and even scenario specific. The context model is set of concepts and their relations which describe circumstances under which the product/machine/components is currently used and possibly the operator behaviour with respect to the scenario in cause.
