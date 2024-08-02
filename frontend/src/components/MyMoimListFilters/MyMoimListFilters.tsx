import MyMoimListFilterTag from '@_components/MyMoimListFilterTag/MyMoimListFilterTag';
import { useState } from 'react';
import * as S from './MyMoimListFilters.style';

const filters = ['모든 모임', '다가오는 모임', '지난 모임'];

export default function MyMoimListFilters() {
  const [selectedFilter, setSelectedFilter] = useState(filters[0]);

  return (
    <div css={S.container}>
      {filters.map((filter) => (
        <MyMoimListFilterTag
          key={filter}
          label={filter}
          isSelected={selectedFilter === filter}
          onClick={() => setSelectedFilter(filter)}
        />
      ))}
    </div>
  );
}
