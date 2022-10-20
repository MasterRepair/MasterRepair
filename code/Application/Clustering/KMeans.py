import numpy as np
import pandas as pd
from sklearn import datasets
from sklearn.cluster import KMeans
from sklearn.metrics import silhouette_score
import matplotlib.pyplot as plt


folderPath = "../../../data/UCRArchive_2018/"
className = "Trace" # Beef, Coffee, Plane, SyntheticControl, Trace
trainSuffix = "_TRAIN"
testSuffix = "_TEST"
dirtySuffix = "_DIRTY"
cleanedSuffix = "_CLEANED"
tsvSuffix = ".tsv"

tdPathPreFix = folderPath + className + "/" + className

train_path = tdPathPreFix + trainSuffix + tsvSuffix
test_path = tdPathPreFix + testSuffix + tsvSuffix
train_dirty_path = tdPathPreFix + trainSuffix + dirtySuffix + tsvSuffix
test_dirty_path = tdPathPreFix + testSuffix + dirtySuffix + tsvSuffix

def cluster_clean(path):
    df_tsv = pd.read_table(path, index_col=None, header=None)
    del df_tsv[0]
    X = df_tsv.values
    estimator = KMeans(n_clusters=6)
    estimator.fit(df_tsv)
    sse = estimator.inertia_
    score = silhouette_score(X, estimator.labels_, metric='euclidean')
    print(score)
def cluster_others(path):
    df_tsv = pd.read_csv(path,sep="   ",index_col=None, header=None,engine='python')
    del df_tsv[0]
    X = df_tsv.values
    estimator = KMeans(n_clusters=6)
    estimator.fit(df_tsv)
    sse = estimator.inertia_
    score = silhouette_score(X, estimator.labels_, metric='euclidean')
    print(score)

cluster_clean(train_path)
cluster_others(train_dirty_path)
cluster_others(folderPath + className + "/Master/" + className + "_TRAIN_CLEANED.tsv")
cluster_others(folderPath + className + "/1NN/" + className + "_TRAIN_CLEANED.tsv")
cluster_others(folderPath + className + "/EditingRule/" + className + "_TRAIN_CLEANED.tsv")