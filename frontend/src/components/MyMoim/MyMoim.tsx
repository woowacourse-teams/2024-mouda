import MoimCardList from '@_components/MoimCardList/MoimCardList';
import MyMoimListFilters, {
  Filter,
} from '@_components/MyMoimListFilters/MyMoimListFilters';
import useMyMoims from '@_hooks/queries/useMyMoims';
import { Fragment, useState } from 'react';

export default function MyMoimList() {
  const [selectedFilter, setSelectedFilter] = useState<Filter['api']>('all');

  const { myMoims, isLoading } = useMyMoims();

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
      {myMoims && <MoimCardList moimInfos={myMoims} />}
    </Fragment>
  );
}
