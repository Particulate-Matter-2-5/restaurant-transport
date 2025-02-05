<template>
    <div>
        <canvas ref="myChart"></canvas>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Chart, registerables } from 'chart.js'
import financialApi from '@/api/financialApi'

Chart.register(...registerables)

const myChart = ref(null)
const chart = ref(null)
const data = ref([])

const getDatas = async () => {
    const { data: res } = await financialApi.getAll()
    data.value = res.data
    console.log('sss', data.value)

    const labels = data.value.map((item) =>
        item.date.split('-').slice(1).join('/')
    )
    const totalData = data.value.map((item) => item.expense)

    // Initialize chart after data is retrieved
    const ctx = myChart.value.getContext('2d')
    chart.value = new Chart(ctx, {
        type: 'line',
        data: {
            labels,
            datasets: [
                {
                    fill: 'origin',
                    backgroundColor: 'rgba(255, 99, 132, 0.2)',
                    borderColor: 'rgba(255, 99, 132, 1)',
                    data: totalData,
                    tension: 0.5,
                    pointRadius: 5,
                },
            ],
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    display: false,
                },
                title: {
                    display: true,
                    text: 'Expense',
                    font: {
                        family: 'Gilroy Bold',
                        size: 30,
                    },
                },
            },
            scales: {
                x: {
                    grid: {
                        display: false,
                    },
                    title: {
                        display: false,
                        font: {
                            family: 'Gilroy Bold',
                            size: 16,
                        },
                    },
                    ticks: {
                        font: {
                            family: 'Gilroy Bold',
                        },
                    },
                },
                y: {
                    beginAtZero: true,
                    grid: {
                        display: true,
                    },
                    title: {
                        display: false,
                        font: {
                            family: 'Gilroy Bold',
                            size: 16,
                        },
                    },
                    ticks: {
                        font: {
                            family: 'Gilroy Bold',
                            size: 14,
                        },
                    },
                },
            },
        },
    })
}

onMounted(() => {
    getDatas()
})
</script>

<style scoped>
canvas {
    max-width: 500px;
    height: 300px;
    margin: auto;
}
</style>
