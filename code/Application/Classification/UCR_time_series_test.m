function UCR_time_series_test(TRAIN, TEST)
 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
   

 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 
 
 TRAIN_class_labels = TRAIN(:,1);   % Pull out the class labels.
 TRAIN(:,1) = [];           % Remove class labels from training set.
 TEST_class_labels = TEST(:,1);    % Pull out the class labels.
 TEST(:,1) = [];           % Remove class labels from testing set.

correct = 0; % Initialize the number we got correct

for i = 1 : length(TEST_class_labels) % Loop over every instance in the test set
    classify_this_object = TEST(i,:);
  this_objects_actual_class = TEST_class_labels(i);
  
  predicted_class = Classification_Algorithm(TRAIN, TRAIN_class_labels, classify_this_object);
  if predicted_class == this_objects_actual_class
    correct = correct + 1;
  end
%   disp([int2str(i), ' out of ', int2str(length(TEST_class_labels)), ' done']) % Report progress
end

%%%%%%%%%%%%%%%%% Create Report %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%  disp(['The dataset you tested has ', int2str(length(unique(TRAIN_class_labels))), ' classes'])
%  disp(['The training set is of size ', int2str(size(TRAIN,1)),', and the test set is of size ',int2str(size(TEST,1)),'.'])
%  disp(['The time series are of length ', int2str(size(TRAIN,2))])
 disp(['The error rate was ',num2str((length(TEST_class_labels)-correct )/length(TEST_class_labels))])
 %%%%%%%%%%%%%%%%% End Report %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 
end