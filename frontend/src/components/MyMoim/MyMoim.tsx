import * as S from './MyMoim.style';

import { Fragment, useState } from 'react';
import MyMoimListFilters, {
  Filter,
} from '@_components/MyMoimListFilters/MyMoimListFilters';

import MoimCardList from '@_components/MoimCardList/MoimCardList';
import useMyMoims from '@_hooks/queries/useMyMoims';

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
      {myMoims && <MoimCardList moimInfos={myMoims} />}
    </Fragment>
  );
}
