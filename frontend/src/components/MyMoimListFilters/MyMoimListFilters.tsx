import * as S from './MyMoimListFilters.style';

import MyMoimListFilterTag from '@_components/MyMoimListFilterTag/MyMoimListFilterTag';

export type Filter =
  | {
      ui: '모든 모임';
      api: 'all';
    }
  | {
      ui: '다가오는 모임';
      api: 'upcoming';
    }
  | {
      ui: '지난 모임';
      api: 'past';
    };

const filters: Filter[] = [
  { ui: '모든 모임', api: 'all' },
  { ui: '다가오는 모임', api: 'upcoming' },
  { ui: '지난 모임', api: 'past' },
];

interface MyMoimListFiltersProps {
  selectedFilter: Filter['api'];
  handleFilterSelect: (filter: Filter['api']) => void;
}

export default function MyMoimListFilters(props: MyMoimListFiltersProps) {
  const { selectedFilter, handleFilterSelect } = props;

  return (
    <div css={S.container}>
      {filters.map(({ ui, api }) => (
        <MyMoimListFilterTag
          key={ui}
          label={ui}
          isSelected={selectedFilter === api}
          onClick={() => handleFilterSelect(api)}
        />
      ))}
    </div>
  );
}
