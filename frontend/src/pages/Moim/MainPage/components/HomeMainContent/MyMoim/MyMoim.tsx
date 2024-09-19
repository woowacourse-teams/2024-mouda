import * as S from './MyMoim.style';

import { Fragment, useState } from 'react';
import MyMoimListFilters, {
  Filter,
} from '@_pages/Moim/MainPage/components/HomeMainContent/MyMoim/MyMoimListFilters/MyMoimListFilters';

import MissingFallback from '@_components/MissingFallback/MissingFallback';
import useMyMoims from '@_hooks/queries/useMyMoims';
import MoimCardList from '../MoimCardList/MoimCardList';

const getFilterString = (filter: Filter['api']) => {
  if (filter === 'all') return '참가한 모임이 없어요';
  if (filter === 'past') return '지나간 모임이 없어요';
  if (filter === 'upcoming') return '다가오는 모임이 없어요';
  return '참가한 모임이 없어요';
};
export default function MyMoimList() {
  const [selectedFilter, setSelectedFilter] = useState<Filter['api']>('all');

  const { myMoims, isLoading } = useMyMoims(selectedFilter);

  const handleFilterSelect = (filter: Filter['api']) => {
    setSelectedFilter(filter);
  };

  if (isLoading) {
    return <div>로딩중...</div>;
  }

  return (
    <Fragment>
      <MyMoimListFilters
        selectedFilter={selectedFilter}
        handleFilterSelect={handleFilterSelect}
      />
      <div css={S.container}></div>
      {!!myMoims?.length && <MoimCardList moimInfos={myMoims} />}
      {(!myMoims || myMoims.length === 0) && (
        <MissingFallback text={getFilterString(selectedFilter)} />
      )}
    </Fragment>
  );
}
