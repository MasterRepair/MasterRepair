clc;

FOLDERNAME = '../../../data/UCRArchive_2018/'; 
DATASETNAEME = 'Beef';
DATASETPATH = [FOLDERNAME, DATASETNAEME, '/'];

% CLEAN
TRAIN = load([DATASETPATH, DATASETNAEME, '_TRAIN.tsv']);
TEST = load([DATASETPATH, DATASETNAEME, '_TEST.tsv']);
"CLEAN"
UCR_time_series_test(TRAIN, TEST);

% DIRTY    
TRAIN = load([DATASETPATH, DATASETNAEME, '_TRAIN_DIRTY.tsv']);
TEST = load([DATASETPATH, DATASETNAEME, '_TEST_DIRTY.tsv']);
"DIRTY"
UCR_time_series_test(TRAIN, TEST);

% Master
TRAIN = load(FOLDERNAME + ""  + DATASETNAEME + "/Master/" + DATASETNAEME + "_TRAIN_CLEANED.tsv");
TEST = load(FOLDERNAME + ""  + DATASETNAEME + "/Master/" + DATASETNAEME + "_TEST_CLEANED.tsv");    
"Master"
UCR_time_series_test(TRAIN, TEST);

% KNN
TRAIN = load(FOLDERNAME + ""  + DATASETNAEME + "/1NN/" + DATASETNAEME + "_TRAIN_CLEANED.tsv");
TEST = load(FOLDERNAME + ""  + DATASETNAEME + "/1NN/" + DATASETNAEME + "_TEST_CLEANED.tsv");    
"1NN"
UCR_time_series_test(TRAIN, TEST);

% EditingRule
TRAIN = load(FOLDERNAME + ""  + DATASETNAEME + "/EditingRule/" + DATASETNAEME + "_TRAIN_CLEANED.tsv");
TEST = load(FOLDERNAME + ""  + DATASETNAEME + "/EditingRule/" + DATASETNAEME + "_TEST_CLEANED.tsv");    
"EditingRule"
UCR_time_series_test(TRAIN, TEST);